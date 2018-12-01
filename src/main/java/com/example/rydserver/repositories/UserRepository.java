package com.example.rydserver.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.rydserver.models.User;

public interface UserRepository  extends CrudRepository<User,Integer>{

}
