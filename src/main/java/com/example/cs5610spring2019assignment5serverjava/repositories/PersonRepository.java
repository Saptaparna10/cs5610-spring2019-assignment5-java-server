package com.example.cs5610spring2019assignment5serverjava.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.cs5610spring2019assignment5serverjava.models.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

}
