package com.enterprise.batch;

import com.enterprise.entity.Department;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration

public class DepartmentJob {

    private DataSource dataSource;
@Autowired
    public DepartmentJob(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private String pathToFile;

    @BeforeStep
    public void retrieveInterStepData(StepExecution stepExecution) {
        // Get the job parameter here (file path)
        this.pathToFile = stepExecution.getJobParameters().getString("fullPathFileName");
    }

    @Bean
    public FlatFileItemReader<Department> read() {
        FlatFileItemReader<Department> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("C:\\Users\\elala\\Downloads\\enterprise\\api/data/test.csv"));

        DefaultLineMapper<Department> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"Department_name"});

        lineMapper.setLineTokenizer(lineTokenizer);
        flatFileItemReader.setLineMapper(lineMapper);
        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }
    @Bean
    public Job jobDe(JobRepository jobRepository, Step step){
    return new JobBuilder("departmentJob",jobRepository)
            .start(step)
            .build();
    }
@Bean
    public Step step(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
    return new StepBuilder("departmentStep",jobRepository)
            .<Department,Department>chunk(10)
            .reader(read())
            .listener(new ChunkListener() {
                @Override
                public void beforeChunk(ChunkContext context) {
                    ChunkListener.super.beforeChunk(context);
                }

                @Override
                public void afterChunk(ChunkContext context) {
                    ChunkListener.super.afterChunk(context);
                    System.out.println("THIS IS WHAT IS GOING TO HAPPEN");
                    System.out.println("THE FILE HAS "+ context.getStepContext().getJobParameters().values());
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
        @Bean
    public ItemWriter<Department> writer() {
    return new JdbcBatchItemWriterBuilder<Department>()
            .dataSource(dataSource)
            .sql("Insert into department(Department_name) values(?)")
            .itemPreparedStatementSetter((item, ps) -> {
                ps.setString(1,item.getDepartment_name());
            })
            .build()
    ;
    }

}
