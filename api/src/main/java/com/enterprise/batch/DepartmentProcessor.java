package com.enterprise.batch;

import com.enterprise.dao.DepartmentRepository;
import com.enterprise.entity.Department;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentProcessor implements org.springframework.batch.item.ItemProcessor<Department, Department> {
    @Autowired
    public DepartmentRepository repository;


    // This is interesting
    @Override
    public Department process(Department item){
        ValidatingItemProcessor<Department> validatingItemProcessor = new ValidatingItemProcessor<>();
        validatingItemProcessor.setFilter(true);
        return item;
    }


}
