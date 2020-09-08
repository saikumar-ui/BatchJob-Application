package com.sevenEleven.springbatchjob2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sevenEleven.springbatchjob2.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
