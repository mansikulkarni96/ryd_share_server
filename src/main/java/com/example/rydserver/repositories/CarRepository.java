package com.example.rydserver.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.rydserver.models.Car;

public interface CarRepository extends CrudRepository<Car,String>{

}