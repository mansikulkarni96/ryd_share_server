package com.example.rydserver.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rydserver.models.User;
import com.example.rydserver.models.Car;
import com.example.rydserver.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*" , allowCredentials = "true" , allowedHeaders = "*")
public class UserService {
	
	List<User> users = new ArrayList<User>();
	@Autowired
	UserRepository userRepository;
	
	
	
	/*Return a list of all the users*/
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List<User>)userRepository.findAll();
		
	}
	
	/*Return a user with user.id equal to id parameter*/
	@GetMapping("/api/user/{id}")
	public User findUserById(@PathVariable ("id") int id) {
		User reqdUser = null;
		for(User u : users)
		{
			if(u.getUserId()==(id))
			{
				reqdUser = u;
			}
		}
		return reqdUser;
	}
	
	@PutMapping("/api/user/{id}")
	public User updateUser(@PathVariable("id") int id, @RequestBody  User user,HttpSession session) {
		User u =  findUserById(id);
		u.update(user);
		return userRepository.save(u);
	}
	
	/*
	If user.username does not already exist, 
	then add the user to the list of registered users 
	and make the user the current user */
	
	@PostMapping("/api/register")
	public User register( @RequestBody User user,HttpSession session) {
		
	
		for(User u : users)
		{
			System.out.println(user.getFirstName());
			if(u.getUsername().equals(user.getUsername()))
			{
				return null;
			}
		}
		session.setAttribute("currentUser", user);
		userRepository.save(user);
		users.add(user);
		return user;
	}
	
	/* Return the current user */
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");	
		return currentUser;
	}
	
	/* Remove the current user from the session */
	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	/* If user.username and password exist
	  then make the user the current user */
	@PostMapping("/api/login")
	public User login(
			@RequestBody User credentials,
			HttpSession session) {
		users= (List<User>) userRepository.findAll();
	 for (User user : users) {
	  if( user.getUsername().equalsIgnoreCase(credentials.getUsername())
	   && user.getPassword().equalsIgnoreCase(credentials.getPassword())) {
	    session.setAttribute("currentUser", user);
	    return user;
	  }
	 }
	 return null;
	}
}
