package com.sevenEleven.springbatchjob2.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {

	@Id
	Integer id;
	String firstName;
	String lastName;
	String email;
	Integer age;

}
