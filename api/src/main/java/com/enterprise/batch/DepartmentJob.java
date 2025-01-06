package com.enterprise.batch;

import com.enterprise.config.DepartmentFieldMapper;
import com.enterprise.entity.Department;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration

public class DepartmentJob {

    private final DataSource dataSource;
//    private final DepartmentProcessor departmentItemProcessor;
//    private final DepartmentValidator validatingItemProcessor;
@Autowired
    public DepartmentJob(DataSource dataSource) {
        this.dataSource = dataSource;

}
    private String pathToFile;

    @BeforeStep
    public void retrieveInterStepData(StepExecution stepExecution) {
        System.out.println("SOMETHING IS ABOUT TO HAPPEN BEFORE THE STEP");
        // Get the job parameter here (file path)
        this.pathToFile = stepExecution.getJobParameters().getString("fullPathFileName");
        System.out.println("THE PATH TO THE UPLOADED FILE IS ===>"+  this.pathToFile);
    }
@Bean
@StepScope
    public FlatFileItemReader<Department> read(@Value("#{jobParameters['fullPathFileName']}") String inputFilePath) {
        FlatFileItemReader<Department> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLinesToSkip(1);
        System.out.println("BEFORE THE ITEM READER " );
        System.out.println("IN THE ITEM READER ======>" + inputFilePath);

            System.out.println("IT is not fucking empty" );
            flatFileItemReader.setResource(new FileSystemResource(inputFilePath));

        DefaultLineMapper<Department> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(new DepartmentFieldMapper());
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"department_name"});
        lineMapper.setLineTokenizer(lineTokenizer);
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;



    }
    @Bean
    public Job jobDe(JobRepository jobRepository, Step step){
    return new JobBuilder("DepartmentJob",jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(step)
            .build();
    }
@Bean
    public Step step(JobRepository jobRepository,PlatformTransactionManager transactionManager,FlatFileItemReader<Department> read) {
    return new StepBuilder("departmentStep",jobRepository)
            .<Department,Department>chunk(10,transactionManager)
            .reader(read)
            .listener(new ChunkListener() {
                @Override
                public void beforeChunk(ChunkContext context) {
                    ChunkListener.super.beforeChunk(context);
                }

                @Override
                public void afterChunk(ChunkContext context) {
                    ChunkListener.super.afterChunk(context);
                    System.out.println("THIS IS WHAT IS GOING TO HAPPEN");
                    String fileName =context.getStepContext().getStepExecution().getJobParameters().getString("fullPathFileName");
                    pathToFile=fileName;
                    System.out.println("THE FILE HAS "+ pathToFile);
                }

                @Override
                public void afterChunkError(ChunkContext context) {
                    ChunkListener.super.afterChunkError(context);
                }
            })
            .writer(writer())
            .transactionManager( transactionManager)
            .build();
    }
//    @Bean
//    public CompositeItemProcessor<Department, Department> compositeProcessor(
//            ) {
//
//        CompositeItemProcessor<Department, Department> compositeProcessor = new CompositeItemProcessor<>();
//        compositeProcessor.setDelegates(Arrays.asList(validatingItemProcessor, departmentItemProcessor));
//        return compositeProcessor;
//    }
        @Bean
    public ItemWriter<Department> writer() {
            return new JdbcBatchItemWriterBuilder<Department>()
                    .dataSource(dataSource)
                    .sql("Insert into department(department_name) values(?)") // Updated to camel_case
                    .itemPreparedStatementSetter((item, ps) -> {
                        ps.setString(1, item.getDepartment_name());
                    })
                    .build();
        }

        }
