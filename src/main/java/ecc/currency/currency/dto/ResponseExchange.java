package ecc.currency.currency.dto;


import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResponseExchange extends Exchange {

  public ResponseExchange(String sourceCurrency, String targetCurrency, double exchangeRate,
      Date effectiveStartDate) {
    super(sourceCurrency, targetCurrency, exchangeRate, effectiveStartDate);
  }

}
