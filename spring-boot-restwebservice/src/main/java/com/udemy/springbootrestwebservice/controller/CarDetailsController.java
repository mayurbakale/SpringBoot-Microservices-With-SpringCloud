package com.udemy.springbootrestwebservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.udemy.springbootrestwebservice.dao.CarDao;
import com.udemy.springbootrestwebservice.exception.NoCarFoundException;
import com.udemy.springbootrestwebservice.exception.NoCarsFoundException;
import com.udemy.springbootrestwebservice.model.CarDetails;
import com.udemy.springbootrestwebservice.repository.CarRepository;
import com.udemy.springbootrestwebservice.repository.OwnerRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses; 

@RestController
public class CarDetailsController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	CarDao carDao;
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/cars/produces", produces="application/vnd.company.app-v1+json")
	@ApiOperation(value="This is a REST Definition for Retrieval of All Cars - Version 1",response=List.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Cars Retrieved Successfully"),
			@ApiResponse(code=404,message="Cars Not Found")
	})
	public MappingJacksonValue getCarsVersion1() {
		List<CarDetails> carList = carDao.getCars().orElseThrow(() -> new NoCarsFoundException("No Cars Found in the System"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","amount");
		FilterProvider filters = new SimpleFilterProvider().addFilter("CarBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(carList);
		mapping.setFilters(filters);
		return mapping;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(path="/cars/produces", produces="application/vnd.company.app-v2+json")
	@ApiOperation(value="This is a REST Definition for Retrieval of All Cars - Version 2",response=List.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Cars Retrieved Successfully"),
			@ApiResponse(code=404,message="Cars Not Found")
	})
	public Resource<CarDetails> getCarsVersion2() {
		CarDetails carDetails = carDao.getCarById(1).get(); 
		
		//hateOAS
		Resource<CarDetails> resource = new Resource<CarDetails>(carDetails);
		ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getCarsVersion1());
		Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash("cars").slash(carDetails.getId()).withSelfRel();
		resource.add(link.withRel("all-cars"));
		resource.add(selfLink);
		
		return resource;
	}
	
	@GetMapping(path="/cars/{id}")
	@ApiOperation(value="This is a REST Definition for retrieving cars by their ID",response=CarDetails.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Cars Retrieved Successfully"),
			@ApiResponse(code=404,message="Cars Not Found")
	})
	// How to use MappingJackSonValue and HateOAS Resource for the same Controller method
	public Resource<CarDetails> getCarById(@PathVariable("id") int id) throws NoCarFoundException {
		if(carDao.getCarById(id).isPresent()) {
			CarDetails carDetails = carDao.getCarById(id).get(); 
			
			//hateOAS
			Resource<CarDetails> resource = new Resource<CarDetails>(carDetails);
			ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).getCarsVersion1());
			Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash("cars").slash(carDetails.getId()).withSelfRel();
			resource.add(link.withRel("all-cars"));
			resource.add(selfLink);
			
			return resource;
		} else {
			throw new NoCarFoundException("Car Not Found in the System");
		}
	}
	
	@PostMapping(path="/cars")
	@ApiOperation(value="This is a REST Definition for Addition of a new Car",response=ResponseEntity.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Cars Retrieved Successfully"),
			@ApiResponse(code=404,message="Cars Not Found")
	})
	public ResponseEntity<Object> insertCars(@Valid @RequestBody CarDetails carDetails) {
		int id = carDao.insertCar(carDetails);
		
		//send the location of the new resource created
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//i18n
	/*@GetMapping("/welcome")
	public String getWelcomePage(@RequestHeader(name="Accept-Language",required=false)Locale locale) {
		return messageSource.getMessage("good.morning.message", null, locale);
	}*/
	
	@GetMapping("/welcome")
	public String getWelcomePage() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	
	@GetMapping("/getAllCars")
	public List<CarDetails> retrieveAllCars() {
		return carRepository.findAll();
	}
	
	@GetMapping("/getCarById/{id}")
	public CarDetails retrieveCarById(@PathVariable int id) {
		Optional<CarDetails> car = carRepository.findById(id);
		return car.orElseThrow(() -> new NoCarsFoundException("No Cars Found in the System"));
	}
	
	@PostMapping(path="/addCar")
	public ResponseEntity<Object> insertCarsData(@Valid @RequestBody CarDetails carDetails) {
		CarDetails car = carRepository.save(carDetails);
		
		//send the location of the new resource created
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(car.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/deleteCarById/{id}")
	public void deleteCarById(@PathVariable int id) {
		carRepository.deleteById(id);
	}
	
}
