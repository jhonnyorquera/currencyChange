package ecc.currency.currency.services.impl;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import ecc.currency.currency.dto.ResponseExchange;
import ecc.currency.currency.repository.CurrencyExchangeRepository;
import ecc.currency.currency.services.CurrencyExchangeService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CurrencyExchangeImpl implements CurrencyExchangeService {

  private CurrencyExchangeRepository exchangeRepository;


  @Override
  public ResponseExchange insertCurrencyExchange(CurrencyExchange currencyExchange){
    currencyExchange.setOutDated(false);
    currencyExchange.setStatus(true);
    exchangeRepository.insert(currencyExchange);
    return new ResponseExchange(currencyExchange.getSourceCurrency(), currencyExchange.getTargetCurrency(), currencyExchange.getExchangeRate());

  }

  @Override
  public CurrencyExchange updateCurrencyExchange(CurrencyExchange currencyExchange) {
    return null;
  }

  @Override
  public boolean deleteCurrencyExchange(CurrencyExchange currencyExchange) {
    return false;
  }

  @Override
  public List<ResponseExchange> retrieveCurrent(RequestExchange requestExchange) {
    return null;
  }

}
