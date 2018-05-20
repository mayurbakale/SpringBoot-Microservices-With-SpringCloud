package com.udemy.springbootrestwebservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Car Details Model")
@JsonFilter("CarBeanFilter")
@Entity
@Table(name="Car")
//@JsonIgnoreProperties({"id","name"})
public class CarDetails {
	
	@ApiModelProperty(dataType="int")
	//@JsonIgnore
	@Id
	@GeneratedValue
	private int id;
	
	@NonNull
	@Size(min=2,message="Name must be atleast 2 characters")
	@ApiModelProperty(dataType="String")
	private String name;
	
	@NonNull
	@ApiModelProperty(dataType="double")
	private double amount;
	
	@OneToMany(mappedBy="car")
	private Set<Owner> ownerList = new HashSet<>();
	
	public CarDetails() {
	}
	
	public CarDetails(int id, @Size(min = 2, message = "Name must be atleast 2 characters") String name,
			@DecimalMax(value = "2", message = "Decimal Amount should only be 2 digits") double amount) {
		super();
		this.id = id;
		this.name = name;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Set<Owner> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(Set<Owner> ownerList) {
		this.ownerList = ownerList;
	}

	@Override
	public String toString() {
		return "CarDetails [id=" + id + ", name=" + name + ", amount=" + amount + ", ownerList=" + ownerList + "]";
	}
}
