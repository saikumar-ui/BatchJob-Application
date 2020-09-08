package com.sevenEleven.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.sevenEleven.springbatch.model.Person;
import com.sevenEleven.springbatch.processor.PersonItemProcessor;
import com.sevenEleven.springbatch.writer.PersonItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	
	@Bean
	public Job importPersonJob() {
		return jobBuilderFactory.get("import-user")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.build();
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("Step1")
				.<Person,Person>chunk(100)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public PersonItemWriter writer() {
		return new PersonItemWriter();
	}

	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public FlatFileItemReader<Person> reader() {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource("src/main/resources/person.csv"));
		reader.setName("CSV-Reader");
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<Person>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setDelimiter(",");
				setStrict(false);
				setNames(new String[] {"id","firstName", "lastName", "email", "age"});
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
				setTargetType(Person.class);
			}});
		}});
		return reader;
	}
}
