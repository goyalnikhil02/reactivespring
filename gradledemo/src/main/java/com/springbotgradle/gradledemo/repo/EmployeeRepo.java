package com.springbotgradle.gradledemo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springbotgradle.gradledemo.model.Person;

public interface EmployeeRepo  extends ReactiveMongoRepository<Person, Integer>{

	
}
