package ecc.currency.currency.repository;

import ecc.currency.currency.domain.CurrencyExchange;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CurrencyExchangeRepository extends MongoRepository<CurrencyExchange, String> {

}
