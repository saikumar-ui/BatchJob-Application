package com.sevenEleven.springbatchjob2;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sevenEleven.springbatchjob2.model.Person;

public class PersonRowMapper implements RowMapper<Person>{

	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

		Person person = new Person();
		person.setId(rs.getInt("id"));
		person.setFirstName(rs.getString("firstName"));
		person.setLastName(rs.getString("laastName"));
		person.setEmail(rs.getString("email"));
		person.setAge(rs.getInt("age"));		
		return person;
	}

}
