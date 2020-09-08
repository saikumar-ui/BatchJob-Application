package com.sevenEleven.springbatch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.sevenEleven.springbatch.model.Person;
import com.sevenEleven.springbatch.repository.PersonRepository;

public class PersonItemWriter implements ItemWriter<Person> {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public void write(List<? extends Person> persons) throws Exception {
		personRepository.saveAll(persons);
		
	}

}
