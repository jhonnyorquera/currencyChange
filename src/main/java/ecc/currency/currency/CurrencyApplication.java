package ecc.currency.currency;

import ecc.currency.currency.domain.CurrencyExchange;
import ecc.currency.currency.repository.CurrencyExchangeRepository;
import java.time.ZonedDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}


	/*
	@Bean
	CommandLineRunner runner(CurrencyExchangeRepository currencyExchangeRepository){

		return args -> {
			CurrencyExchange currencyExchange= new CurrencyExchange();
			currencyExchange.setSourceCurrency("USD");
			currencyExchange.setTargetCurrency("COP");
			currencyExchangeRepository.insert(currencyExchange);
		};
	}
*/
}
