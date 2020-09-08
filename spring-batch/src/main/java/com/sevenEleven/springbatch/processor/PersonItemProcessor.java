package com.sevenEleven.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.sevenEleven.springbatch.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		Person transformedPerson = new Person();
		transformedPerson.setId(person.getId());
		transformedPerson.setFirstName(person.getFirstName().toLowerCase());
		transformedPerson.setLastName(person.getLastName().toLowerCase());
		transformedPerson.setEmail(person.getEmail());
		transformedPerson.setAge(person.getAge());
		return transformedPerson;
	}

}
