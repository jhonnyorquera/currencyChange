package ecc.currency.currency.services;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import ecc.currency.currency.dto.ResponseExchange;
import java.util.List;

public interface CurrencyExchangeService {

  ResponseExchange insertCurrencyExchange(CurrencyExchange currencyExchange);

  CurrencyExchange updateCurrencyExchange(CurrencyExchange currencyExchange);

  boolean deleteCurrencyExchange(CurrencyExchange currencyExchange);

  List<ResponseExchange> retrieveCurrent(RequestExchange requestExchange);

}
