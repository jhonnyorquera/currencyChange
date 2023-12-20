package ecc.currency.currency.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseExchange {
private String sourceExchange;
private String targetExchange;
  private double exchangeRate;
}
