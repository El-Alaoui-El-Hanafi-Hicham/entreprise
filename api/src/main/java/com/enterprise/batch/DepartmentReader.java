//package com.enterprise.batch;
//
//import com.enterprise.entity.Department;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.NonTransientResourceException;
//import org.springframework.batch.item.ParseException;
//import org.springframework.batch.item.UnexpectedInputException;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DepartmentReader  {
//    @Bean
//    public FlatFileItemReader<Department> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        FlatFileItemReader flatFileItemReader = new FlatFileItemReader<>();
//        DefaultLineMapper lineMapper = new DefaultLineMapper();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setNames(new String[]{"Department_name"});
//        lineMapper.setLineTokenizer(lineTokenizer);
//        flatFileItemReader.setLinesToSkip(1);
//        flatFileItemReader.setLineMapper(lineMapper);
//        flatFileItemReader.setResource(new FileSystemResource(pathToFile));
//        return flatFileItemReader;
//    }
//
//
//    }
