package com.sevenEleven.springbatchjob2.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sevenEleven.springbatchjob2.PersonRowMapper;
import com.sevenEleven.springbatchjob2.model.Person;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	public Job importPersonJob() {
		return jobBuilderFactory.get("load-user")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
	

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("load-user-step")
				.<Person, Person> chunk(10)
				.reader(personItemReader())
				.writer(personItemWritter())
				.build();		
	}
	
	
	@Bean
	public FlatFileItemWriter<Person> personItemWritter() {
		System.out.println("calling item writter");
		FlatFileItemWriter<Person> writer=new FlatFileItemWriter<Person>();
		writer.setResource(new  ClassPathResource("person.csv"));
		DelimitedLineAggregator<Person> aggregator=new DelimitedLineAggregator<Person>();
		aggregator.setDelimiter(",");
		BeanWrapperFieldExtractor<Person> feildExtractor=new BeanWrapperFieldExtractor<Person>();
		feildExtractor.setNames(new String[] {"id","first_name","last_name","email","age"});
		aggregator.setFieldExtractor(feildExtractor);
		writer.setLineAggregator(aggregator);
		return writer;
	}

	@Bean
	public JdbcCursorItemReader<Person> personItemReader() {
		 JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<Person>();
		  reader.setDataSource(dataSource);
		  reader.setSql("SELECT id, first_name, last_name, email, age FROM person");
		  reader.setRowMapper(new PersonRowMapper());
		  System.out.println(".........Reader.........");
		  return reader;
	}
}
