package com.udemy.springbootrestwebservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udemy.springbootrestwebservice.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer>{

}
