package ecc.currency.currency.domain;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class CurrencyExchange {


  @Id
  private String id;

  private String sourceCurrency;
  private String targetCurrency;
  private ZonedDateTime effectiveStartDate;
  private double exchangeRate;
  private Boolean status;
  private Boolean outDated;


  public CurrencyExchange() {

  }
}
