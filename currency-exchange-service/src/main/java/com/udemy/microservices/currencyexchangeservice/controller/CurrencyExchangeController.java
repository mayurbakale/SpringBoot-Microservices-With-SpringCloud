package com.udemy.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.microservices.currencyexchangeservice.bean.ExchangeValue;
import com.udemy.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@GetMapping("/currency-exchange/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to) {
		/*ExchangeValue value = new ExchangeValue(1000L,from,to,BigDecimal.valueOf(65));
		value.setPort(Integer.parseInt(environment.getProperty("local.server.port")));*/
		ExchangeValue value = repository.findByFromAndTo(from, to);
		value.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return value;
	}
}
