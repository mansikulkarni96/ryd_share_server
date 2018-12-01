package com.example.rydserver.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Car> cars = new ArrayList<Car>();
	
//	public User(String user) {
//		this.username
//		
//	}
	
	public int getUserId() {
		
		return id;
	}
	
	public void setUserId(int userId) {
		
		this.id = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Car> getCar() {
		return cars;
	}
	public void setCar(List<Car> cars) {
		this.cars = cars;
	}
	public void update(User user) {
		this.username = user.username != null ?  user.username : this.username;
		this.password = user.password != null ? user.password : this.password;
		this.firstName = user.firstName != null ?  user.firstName : this.firstName;
		this.lastName = user.lastName != null ? user.lastName : this.lastName;
	}
}
