package com.enterprise.config;

import com.enterprise.entity.Department;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class DepartmentFieldMapper implements org.springframework.batch.item.file.mapping.FieldSetMapper<Department> {
    @Override
    public Department mapFieldSet(FieldSet fieldSet) throws BindException {
        Department department= new Department();
        department.setDepartment_name(fieldSet.readString("department_name"));
        System.out.println("THE DEPARTMENTS AREEEEEEEEEEEEEEEEEEE");
        System.out.println(department);
        return department;
    }
}
