package ecc.currency.currency.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.util.Date;
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
  @JsonFormat(pattern="yyyy-MM-dd")
  private Date effectiveStartDate;
  private double exchangeRate;
  private Boolean status;


  public CurrencyExchange() {

  }
}
