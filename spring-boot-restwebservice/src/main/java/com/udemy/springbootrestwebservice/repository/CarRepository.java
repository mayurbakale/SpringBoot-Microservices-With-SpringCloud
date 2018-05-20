package com.udemy.springbootrestwebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.springbootrestwebservice.model.CarDetails;

@Repository
public interface CarRepository extends JpaRepository<CarDetails, Integer>{

}
