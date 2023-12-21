package ecc.currency.currency.repository;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import java.util.Date;
import java.util.List;

public interface CurrencyExchangeCriteriaRepository {


 List<CurrencyExchange> retrieveCurrencyExchangeByTargetCurrencyAndStatusAndEffectiveStaDate(String target, Date date) ;

 List<CurrencyExchange> retrieveCurrencyExchangeByTargetAndSourceAndStatusAndEffectiveStaDate(
     RequestExchange requestExchange);

 List<CurrencyExchange> retrieveCurrencyExchangeByTargetAndSourceAndStatus(
     RequestExchange requestExchange);

 List<CurrencyExchange> retrieveCurrencyExchangeBySourceCurrencyAndStatusAndEffectiveStaDate(String target,
     Date date);



}
