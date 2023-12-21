package ecc.currency.currency.repository.impl;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import ecc.currency.currency.repository.CurrencyExchangeCriteriaRepository;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyExchangeCriteriaRepositoryImpl implements CurrencyExchangeCriteriaRepository {


  private MongoTemplate mongoTemplate;

  @Override
  public List<CurrencyExchange> retrieveCurrencyExchangeByTargetCurrencyAndStatusAndEffectiveStaDate(String target,
      Date date) {
    Query query = new Query();
    query.addCriteria(Criteria.where("targetCurrency").is(target))
        .addCriteria(Criteria.where("status").is(true))
        .addCriteria(Criteria.where("effectiveStartDate").lt(date));
    return mongoTemplate.find(query, CurrencyExchange.class);
  }

  @Override
  public List<CurrencyExchange> retrieveCurrencyExchangeByTargetAndSourceAndStatusAndEffectiveStaDate(
      RequestExchange requestExchange) {
    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(requestExchange.getSourceCurrency()))
        .addCriteria(Criteria.where("targetCurrency").is(requestExchange.getTargetCurrency()))
        .addCriteria(Criteria.where("effectiveStartDate").lt(requestExchange.getEffectiveStartDate()))
        .addCriteria(Criteria.where("status").is(true));
    return mongoTemplate.find(query, CurrencyExchange.class);
  }

  @Override
  public List<CurrencyExchange> retrieveCurrencyExchangeByTargetAndSourceAndStatus(RequestExchange requestExchange) {

    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(requestExchange.getSourceCurrency()))
        .addCriteria(Criteria.where("targetCurrency").is(requestExchange.getTargetCurrency()))
        .addCriteria(Criteria.where("status").is(true));

    return mongoTemplate.find(query, CurrencyExchange.class);
  }

  @Override
  public List<CurrencyExchange> retrieveCurrencyExchangeBySourceCurrencyAndStatusAndEffectiveStaDate(String target,
      Date date) {
    Query query = new Query();
    query.addCriteria(Criteria.where("sourceCurrency").is(target))
        .addCriteria(Criteria.where("status").is(true))
        .addCriteria(Criteria.where("effectiveStartDate").lt(date));
    return mongoTemplate.find(query, CurrencyExchange.class);
  }
}
