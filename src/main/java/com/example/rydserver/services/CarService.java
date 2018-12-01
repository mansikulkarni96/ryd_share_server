package com.example.rydserver.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rydserver.models.Car;
import com.example.rydserver.models.User;

import com.example.rydserver.repositories.CarRepository;

@RestController
@CrossOrigin(origins = "*" , allowCredentials = "true" , allowedHeaders = "*")
public class CarService {
	
	List<Car> cars = new ArrayList<Car>();
	@Autowired
	CarRepository carRepository;
	
	@GetMapping("/api/cars")
	public Iterable<Car> findAllCourses() {
		return carRepository.findAll();
	}
	
	@GetMapping("/api/user/{userId}/car")
	public List<Car> findAllCourse(HttpSession session) {
		//Iterable<Course> courses = courseRepository.findAll();
		User currentUser = (User)session.getAttribute("currentUser");
		if(currentUser==null)
		{
			return null;
		}
		cars = currentUser.getCar();
		return cars;
	}
	
	@PostMapping("/api/car/{carId}/start")
	public Car createCourse(@RequestBody Car car,HttpSession session) {
		
		car.setUser((User)session.getAttribute("currentUser"));
		return carRepository.save(car);
	}
	
	@GetMapping("/api/car/{carId}")
	public Car findCarById(@PathVariable("carId") String id,HttpSession session) {
		Car car = null;
		Optional<Car> reqdCourse = carRepository.findById(id);
		if(reqdCourse.isPresent())
		{
			car =  reqdCourse.get();
		}
		else {
			for (Car c : cars) {
				if (c.getId() == id) {
					car = c;
				}
			}
		}
		return car;
	}
	
	/*@PutMapping("/api/course/{cid}")
	public Car updateCourse(@PathVariable("cid") String cid, @RequestBody Car car,HttpSession session) {
		Car cr =  findCarById(cid,session);
		cr.update(car);
		return carRepository.save(cr);
	}*/
	
	@DeleteMapping("/api/car/{carId}/end")
	public void deleteCar(@PathVariable("cid") String id,HttpSession session) {
		carRepository.deleteById(id);
	}
}
