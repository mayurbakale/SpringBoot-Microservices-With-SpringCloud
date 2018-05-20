package com.udemy.springbootrestwebservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.udemy.springbootrestwebservice.model.CarDetails;

@Repository
public class CarDao {

	private static List<CarDetails> carList = new ArrayList<>();
	
	static {
		carList.add(new CarDetails(1,"Maruti Swift",100.0));
		carList.add(new CarDetails(2,"Honda City",200.0));
		carList.add(new CarDetails(3,"Hyundai i10",150.0));
	}
	
	public Optional<List<CarDetails>> getCars() {
		return Optional.of(carList);
		//return Optional.empty();
	}
	
	public Optional<CarDetails> getCarById(int id) {
		return carList.stream().filter(c -> c.getId() == id).findAny();
	}

	public int insertCar(CarDetails carDetails) {
		carList.add(carDetails);
		return carDetails.getId();
	}
	
	
}
