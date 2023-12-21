package ecc.currency.currency.services.impl;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import ecc.currency.currency.dto.ResponseExchange;
import ecc.currency.currency.repository.CurrencyExchangeRepository;
import ecc.currency.currency.services.CurrencyExchangeService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CurrencyExchangeImpl implements CurrencyExchangeService {

  private CurrencyExchangeRepository exchangeRepository;

  private MongoTemplate mongoTemplate;


  @Override
  public ResponseExchange insertCurrencyExchange(CurrencyExchange currencyExchange) {
    currencyExchange.setStatus(true);
    if (canInsert(currencyExchange)) {
      exchangeRepository.insert(currencyExchange);
      ResponseExchange responseExchange = new ResponseExchange();
      responseExchange.setSourceCurrency(currencyExchange.getSourceCurrency());
      responseExchange.setTargetCurrency(currencyExchange.getTargetCurrency());
      responseExchange.setExchangeRate(currencyExchange.getExchangeRate());
      responseExchange.setEffectiveStartDate(currencyExchange.getEffectiveStartDate());
      return responseExchange;
    }
    return null;
  }

  private Boolean canInsert(CurrencyExchange currencyExchange) {

    if (currencyExchange.getSourceCurrency().equals(currencyExchange.getTargetCurrency())) {
      throw new RuntimeException("Target and Source Exchange are equals, please review");
    }

    if (currencyExchange.getExchangeRate() == 0) {
      throw new RuntimeException("Exchange Rate can´t be null or zero, please review");
    }

    RequestExchange requestExchange = new RequestExchange(currencyExchange.getSourceCurrency(),
        currencyExchange.getTargetCurrency(), currencyExchange.getExchangeRate(),
        currencyExchange.getEffectiveStartDate());
    if (retrieveCurrencyExchangeByTargetAndSourceAndStatus(requestExchange) != null) {
      throw new RuntimeException("Currency Exchange already exists");
    }
    return true;
  }

  @Override
  public String updateCurrencyExchange(RequestExchange currencyExchange) {
    CurrencyExchange retrieveCuEx = retrieveCurrencyExchangeByTargetAndSourceAndStatus(currencyExchange);
    if (retrieveCuEx == null) {
      throw new RuntimeException("Currency Exchange not exist");
    }
    if (currencyExchange.getExchangeRate() != 0.0) {
      retrieveCuEx.setExchangeRate(currencyExchange.getExchangeRate());
    }
    if (currencyExchange.getEffectiveStartDate() != null) {
      retrieveCuEx.setEffectiveStartDate(currencyExchange.getEffectiveStartDate());
    }
    exchangeRepository.save(retrieveCuEx);
    return "Exchange Currency Updated";
  }

  @Override
  public Boolean deleteCurrencyExchange(RequestExchange currencyExchange) {
    CurrencyExchange cuExForDelete = retrieveCurrencyExchangeByTargetAndSourceAndStatus(currencyExchange);
    if (cuExForDelete == null) {
      throw new RuntimeException("Currency Exchange not exist");
    }
    cuExForDelete.setStatus(false);
    exchangeRepository.save(cuExForDelete);

    return true;
  }

  @Override
  public ResponseExchange retrieveCurrent(RequestExchange requestExchange) {

    List<ResponseExchange> responseExchangeList = new ArrayList<>();
    CurrencyExchange currencyExchangeExact;

    currencyExchangeExact = retrieveCurrencyExchangeByTargetAndSourceAndStatusAndEffectiveStaDate(requestExchange);
    if (currencyExchangeExact != null) {
      return mapCurrencyExchange(currencyExchangeExact);
    } else {
      return responseExchangeListByTriangularMethod(requestExchange);
    }

  }

  private List<ResponseExchange> getResponseExchangePath(List<CurrencyExchange> currencyExchangesSource,
      List<CurrencyExchange> currencyExchangeTarget) {

    List<ResponseExchange> responseExchangesPath = new ArrayList<>();
    currencyExchangesSource.stream().forEach(source -> {
      currencyExchangeTarget.stream().forEach(target ->
      {
        if (source.getTargetCurrency().equals(target.getSourceCurrency())) {
          responseExchangesPath.add(mapCurrencyExchange(source));
          responseExchangesPath.add(mapCurrencyExchange(target));
        }
      });
    });

    return responseExchangesPath;
  }

  private ResponseExchange mapCurrencyExchange(CurrencyExchange currencyExchange) {
    return new ResponseExchange(currencyExchange.getSourceCurrency(), currencyExchange.getTargetCurrency(),
        currencyExchange.getExchangeRate(), currencyExchange.getEffectiveStartDate());
  }

  private ResponseExchange responseExchangeListByTriangularMethod(RequestExchange requestExchange) {

    List<CurrencyExchange> sourceCurrency =
        retrieveCurrencyExchangeBySourceCurrencyAndStatusAndEffectiveStaDate(requestExchange.getSourceCurrency(),
            requestExchange.getEffectiveStartDate());
    if (sourceCurrency.size() < 1) {
      throw new RuntimeException("Source Currency Exchange not exist");
    }

    List<CurrencyExchange> targetCurrency =
        retrieveCurrencyExchangeByTargetCurrencyAndStatusAndEffectiveStaDate(requestExchange.getTargetCurrency(),
            requestExchange.getEffectiveStartDate());
    if (targetCurrency.size() < 1) {
      throw new RuntimeException("Target Currency Exchange not exist");
    }

    List<ResponseExchange> pathConversion = getResponseExchangePath(sourceCurrency, targetCurrency);

    if (pathConversion.size() > 1) {
      return new ResponseExchange(pathConversion.get(0).getSourceCurrency(), pathConversion.get(1).getTargetCurrency(),
          Math.floor(pathConversion.get(0).getExchangeRate() * pathConversion.get(1).getExchangeRate() * 10000) / 10000
          , requestExchange.getEffectiveStartDate());

    } else {
      throw new RuntimeException("There isn´t a way for conversion");
    }

  }

  private List<CurrencyExchange> retrieveCurrencyExchangeBySourceCurrencyAndStatusAndEffectiveStaDate(String target,
      Date date) {
    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(target))
        .addCriteria(Criteria.where("status").is(true))
        .addCriteria(Criteria.where("effectiveStartDate").lt(date));
    return mongoTemplate.find(query, CurrencyExchange.class);

  }

  private List<CurrencyExchange> retrieveCurrencyExchangeByTargetCurrencyAndStatusAndEffectiveStaDate(String target,
      Date date) {
    Query query = new Query();
    query.addCriteria(Criteria.where("targetCurrency").is(target))
        .addCriteria(Criteria.where("status").is(true))
        .addCriteria(Criteria.where("effectiveStartDate").lt(date));
    return mongoTemplate.find(query, CurrencyExchange.class);

  }

  private CurrencyExchange retrieveCurrencyExchangeByTargetAndSourceAndStatus(
      RequestExchange requestExchange) {

    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(requestExchange.getSourceCurrency()))
        .addCriteria(Criteria.where("targetCurrency").is(requestExchange.getTargetCurrency()))
        .addCriteria(Criteria.where("status").is(true));

    List<CurrencyExchange> currencyExchangesList = mongoTemplate.find(query, CurrencyExchange.class);
    if (currencyExchangesList.size() > 0) {
      return currencyExchangesList.get(0);
    }
    return null;

  }


  private CurrencyExchange retrieveCurrencyExchangeByTargetAndSourceAndStatusAndEffectiveStaDate(
      RequestExchange requestExchange) {

    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(requestExchange.getSourceCurrency()))
        .addCriteria(Criteria.where("targetCurrency").is(requestExchange.getTargetCurrency()))
        .addCriteria(Criteria.where("effectiveStartDate").lt(requestExchange.getEffectiveStartDate()))
        .addCriteria(Criteria.where("status").is(true));

    List<CurrencyExchange> currencyExchangesList = mongoTemplate.find(query, CurrencyExchange.class);
    if (currencyExchangesList.size() > 0) {
      return currencyExchangesList.get(0);
    }
    return null;

  }


}
