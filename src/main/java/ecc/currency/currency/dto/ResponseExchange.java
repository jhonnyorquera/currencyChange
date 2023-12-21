package ecc.currency.currency.dto;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseExchange {

  private String sourceCurrency;
  private String targetCurrency;
  private double exchangeRate;
  private Date effectiveStartDate;

  public ResponseExchange() {

  }
}
