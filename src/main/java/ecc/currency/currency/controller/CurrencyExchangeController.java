package ecc.currency.currency.controller;


import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.dto.RequestExchange;
import ecc.currency.currency.dto.ResponseExchange;
import ecc.currency.currency.services.CurrencyExchangeService;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/currency")
@AllArgsConstructor
public class CurrencyExchangeController {

  private CurrencyExchangeService currencyExchangeService;


  @PutMapping("/")
  public ResponseEntity<ResponseExchange> insertCurrencyExchange(@RequestBody CurrencyExchange currencyExchange) {

    return new ResponseEntity<ResponseExchange>(currencyExchangeService.insertCurrencyExchange(currencyExchange),
        HttpStatus.CREATED);

  }

  @PostMapping("/")
  public ResponseEntity<String> updateCurrencyExchange(@RequestBody RequestExchange requestExchange) {
    String response = currencyExchangeService.updateCurrencyExchange(requestExchange);
    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
  }

  @DeleteMapping("/")
  public ResponseEntity<Void> deleteCurrencyExchange(@RequestBody RequestExchange requestExchange) {
    currencyExchangeService.deleteCurrencyExchange(requestExchange);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @GetMapping("/")
  public ResponseEntity<List<ResponseExchange>> getCurrencyExchange(@RequestParam String sourceCurrency,
                                                                  @RequestParam String targetCurrency,
                                                                  @RequestParam Date effectiveStartDate) {
    RequestExchange request= new RequestExchange();
    request.setSourceCurrency(sourceCurrency);
    request.setTargetCurrency(targetCurrency);
    request.setEffectiveStartDate(effectiveStartDate);
    return new ResponseEntity<List<ResponseExchange>>(currencyExchangeService.retrieveCurrent(request), HttpStatus.ACCEPTED);
  }

}
