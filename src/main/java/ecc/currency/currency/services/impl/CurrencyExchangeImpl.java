package ecc.currency.currency.services.impl;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import ecc.currency.currency.dto.ResponseExchange;
import ecc.currency.currency.repository.CurrencyExchangeRepository;
import ecc.currency.currency.services.CurrencyExchangeService;
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
    currencyExchange.setOutDated(false);
    currencyExchange.setStatus(true);
    if (canInsert(currencyExchange)) {
      exchangeRepository.insert(currencyExchange);
      ResponseExchange responseExchange = new ResponseExchange();
      responseExchange.setSourceCurrency(currencyExchange.getSourceCurrency());
      responseExchange.setTargetCurrency(currencyExchange.getTargetCurrency());
      responseExchange.setExchangeRate(currencyExchange.getExchangeRate());
      return responseExchange;
    }
    return null;
  }

  private Boolean canInsert(CurrencyExchange currencyExchange) {

   if(currencyExchange.getSourceCurrency().equals(currencyExchange.getTargetCurrency())){
     throw new RuntimeException("Target and Source Exchange are equals, please review");
   }

    RequestExchange requestExchange = new RequestExchange(currencyExchange.getSourceCurrency(),
        currencyExchange.getTargetCurrency(), currencyExchange.getExchangeRate(),
        currencyExchange.getEffectiveStartDate());
    if (retrieveCurrencyExchangeByTargetAndSourceAndStatusAndOutdated(requestExchange) != null) {
      throw new RuntimeException("Currency Exchange already exists");
    }
    return true;
  }

  @Override
  public String updateCurrencyExchange(RequestExchange currencyExchange) {
    CurrencyExchange retrieveCuEx = retrieveCurrencyExchangeByTargetAndSourceAndStatusAndOutdated(currencyExchange);
    if(retrieveCuEx==null){
      throw new RuntimeException("Currency Exchange not exist");
    }
    retrieveCuEx.setExchangeRate(currencyExchange.getExchangeRate());
    if (currencyExchange.getEffectiveStartDate() != null) {
      retrieveCuEx.setEffectiveStartDate(currencyExchange.getEffectiveStartDate());
    }

    exchangeRepository.save(retrieveCuEx);

    return "Exchange Currency Updated";
  }

  @Override
  public boolean deleteCurrencyExchange(CurrencyExchange currencyExchange) {
    return false;
  }

  @Override
  public List<ResponseExchange> retrieveCurrent(RequestExchange requestExchange) {
    return null;
  }


  private CurrencyExchange retrieveCurrencyExchangeByTargetAndSourceAndStatusAndOutdated(
      RequestExchange requestExchange) {

    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(requestExchange.getSourceCurrency()))
        .addCriteria(Criteria.where("targetCurrency").is(requestExchange.getTargetCurrency()))
        .addCriteria(Criteria.where("status").is(true))
        .addCriteria(Criteria.where("outDated").is(false));

    List<CurrencyExchange> currencyExchangesList = mongoTemplate.find(query, CurrencyExchange.class);
    if (currencyExchangesList.size() > 0) {
      return currencyExchangesList.get(0);
    }
    return null;

  }

}
