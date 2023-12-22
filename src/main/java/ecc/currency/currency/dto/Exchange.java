package ecc.currency.currency.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Exchange {

  private String sourceCurrency;
  private String targetCurrency;
  private double exchangeRate;
  private Date effectiveStartDate;


}
