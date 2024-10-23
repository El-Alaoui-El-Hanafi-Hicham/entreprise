//package com.enterprise.batch;
//
//import com.enterprise.dao.EmployeeRepository;
//import com.enterprise.entity.Employee;
//import org.springframework.batch.item.Chunk;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Writer implements ItemWriter<Employee> {
//    @Autowired
//    EmployeeRepository employeeRepository;
//    @Override
//    @Bean
//    public void write(Chunk<? extends Employee> chunk) throws Exception {
//        System.out.println(chunk);
//
//        chunk.getItems().stream().map(el->employeeRepository.save(el));
//    }
//}
