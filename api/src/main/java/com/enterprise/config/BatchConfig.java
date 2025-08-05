package com.enterprise.config;

import com.enterprise.entity.Department;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.WritableResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration

public class BatchConfig {

    private final PlatformTransactionManager transactionManager;

    private final JobRepository jobRepository;
    private final DataSource dataSource;

    public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository, DataSource dataSource) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.dataSource = dataSource;
    }

//    @Bean
//    public Step step(JobRepository jobRepository) {
//        return new StepBuilder("step",jobRepository)
//                .tasklet(tasklet()) // Using a separate method for tasklet
//                .transactionManager(transactionManager)  // Inject the transaction manager
//                .build();
//    }
@Bean
public  Step step2() throws Exception {
    return new StepBuilder("step 2",this.jobRepository)
            .<Department,Department>chunk(5)
            .reader(itemReader())
//            .processor(itemProcessor())
            .writer(DBItemWriter())

            .transactionManager(transactionManager)  // Inject the transaction manager

            .build();
    }



    @Bean
    public ItemWriter<? super Department> DBItemWriter() {
//    return new JdbcBatchItemWriterBuilder<Department>()
//            .dataSource(dataSource)
//            .sql("INSERT into department(department_name) values(?)")
//            .itemPreparedStatementSetter(new ItemPreparedStatementSetter<Department>() {
//                @Override
//                public void setValues(Department item, PreparedStatement ps) throws SQLException {
//                    ps.setString(1,item.getDepartment_name());
//
//                }
//            })
//            .build();
    return new JsonFileItemWriterBuilder<Department>()
            .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
            .name("JSON F")
            .resource(new FileSystemResource("C:\\Users\\elala\\Downloads\\enterprise\\api/data/Written.json"))
            .build()
            ;
    }

    @Bean
    public  Step step1() throws Exception {
        return new StepBuilder("step",this.jobRepository)
                .<Department,Department>chunk(5)
                .reader(itemReader())
                .writer(itemWriter())
                .transactionManager(transactionManager)  // Inject the transaction manager

                .build();

//         .writer(new ItemWriter<Department>() {
//            @Override
//            public void write(Chunk<? extends Department> chunk) throws Exception {
//                System.out.println(chunk.size());
//                chunk.forEach(System.out::println);
//            }
//        })
    }

    @Bean
    public ItemWriter<Department> itemWriter() throws MalformedURLException {
        FlatFileItemWriter<Department> flatFileItemWriter = new FlatFileItemWriter<>();

        // Set the output resource to a specific file location
        flatFileItemWriter.setResource(new FileSystemResource("C:\\Users\\elala\\Downloads\\enterprise\\api/data/written.csv")); // Specify an absolute path if needed
        flatFileItemWriter.setAppendAllowed(false); // Set to true if you want to append instead of overwrite

        // Ensure the line aggregator and field extractor are set correctly
        DelimitedLineAggregator<Department> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Department> departmentBeanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        departmentBeanWrapperFieldExtractor.setNames(new String[]{"department_name"});
        lineAggregator.setFieldExtractor(departmentBeanWrapperFieldExtractor);

        flatFileItemWriter.setLineAggregator(lineAggregator);

        System.out.println("Writer initialized and ready to write data.");
        return flatFileItemWriter;
    }


    private ItemReader<Department> itemReader() throws Exception {
        FlatFileItemReader<Department> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(new FileSystemResource("C:\\Users\\elala\\Downloads\\enterprise\\api/data/test.csv"));
        DefaultLineMapper<Department> lineMapper = new DefaultLineMapper<>();
        lineMapper.setFieldSetMapper(new DepartmentFieldMapper());
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"department_name"});
        lineMapper.setLineTokenizer(lineTokenizer);
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;


//        return new JdbcCursorItemReaderBuilder<Department>()
//                .dataSource(dataSource)
//                .name("JDBC Cursor")
//                .sql("SELECT * from department ORDER BY id")
//                .rowMapper(new JdbcTestMapper())
//                .build();

//        return new JdbcPagingItemReaderBuilder<Department>()
//                .dataSource(dataSource)
//                .name("JDBC Cursor")
//                .queryProvider(queryProvider())
//                .rowMapper(new JdbcTestMapper())
//                .pageSize(5)
//                .build();
    }
    @Bean
        public PagingQueryProvider queryProvider() throws Exception {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
            SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
            factoryBean.setSortKeys(sortKeys);
        factoryBean.setSelectClause("SELECT *");
        factoryBean.setFromClause("FROM department");
        factoryBean.setDataSource(dataSource); // Assumes `dataSource` is properly defined
            return factoryBean.getObject();
        }


    @Bean
    public Tasklet tasklet() {
        return (contribution, chunkContext) -> {
            System.out.println("let's hope it works");
            // Handle any additional logic or error handling here
            return RepeatStatus.FINISHED;
        };
    }

//s
}
