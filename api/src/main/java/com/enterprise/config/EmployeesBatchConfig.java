package com.enterprise.config;

import com.enterprise.entity.Employee;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration

public class EmployeesBatchConfig {
    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;
    private final DataSource dataSource;


    public EmployeesBatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository, DataSource dataSource) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcBatchItemWriter<Employee> employeeDbWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        System.out.println("this is happening")     ;
        writer.setSql("INSERT INTO employee (firstName, lastName, email, job_title, phone_number, hire_date) VALUES (?, ?, ?, ?, ?, ?)");
        writer.setItemPreparedStatementSetter((employee, ps) -> {
            System.out.println("Writing employee: " + employee.getFirstName() + " " + employee.getLastName());
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getJobTitle());
            ps.setString(5, employee.getPhoneNumber());
            ps.setDate(6, new java.sql.Date(employee.getHireDate().getTime()));
        });
        return writer;
    }

    @Bean
    public Step saveToDbStep() {
        return new StepBuilder("saveToDbStep", jobRepository)
                .<Employee, Employee>chunk(5, transactionManager)
                .reader(empitemReader(null)) // Spring will inject the correct value at runtime
                .writer(employeeDbWriter(dataSource))
                .build();
    }

    @Bean
    public Job EmployeeJob(Step saveToDbStep) {
        return new JobBuilder("EmpJob", jobRepository)
                .start(saveToDbStep)
                .build();
    }

    // This method is kept for potential future use but removed hardcoded paths
    @Bean
    public ItemWriter<Employee> empItemWriter() {
        FlatFileItemWriter<Employee> flatFileItemWriter = new FlatFileItemWriter<>();
        // Use system temp directory instead of hardcoded path
        flatFileItemWriter.setResource(new FileSystemResource(System.getProperty("java.io.tmpdir") + "/employee_output.csv"));
        flatFileItemWriter.setAppendAllowed(false);
        DelimitedLineAggregator<Employee> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Employee> employeeBeanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        employeeBeanWrapperFieldExtractor.setNames(new String[]{"firstName","lastName","email","job_title","phone_number","hire_date"});
        lineAggregator.setFieldExtractor(employeeBeanWrapperFieldExtractor);
        flatFileItemWriter.setLineAggregator(lineAggregator);
        return flatFileItemWriter;
    }

    @Bean
    @StepScope
    public ItemReader<Employee> empitemReader(@Value("#{jobParameters['fullPathFileName']}") String filePath) {
        FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(new FileSystemResource(filePath));
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(new EmployeeFieldMapper());
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("firstName","lastName","email","job_title","phone_number","hire_date");
        lineMapper.setLineTokenizer(lineTokenizer);
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }
}
