package com.enterprise.config;

import com.enterprise.entity.Department;
import com.enterprise.entity.Employee;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class EmployeeFieldMapper implements org.springframework.batch.item.file.mapping.FieldSetMapper<Employee> {
    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        Employee employee= new Employee();
        System.out.println(fieldSet.readString("first_name"));
        employee.setFirst_name(fieldSet.readString("first_name"));
        employee.setLast_name(fieldSet.readString("last_name"));
        employee.setEmail(fieldSet.readString("email"));
        employee.setJob_title(fieldSet.readString("job_title"));
        employee.setPhone_number(fieldSet.readInt("phone_number"));
        employee.setHire_date(fieldSet.readDate("hire_date"));
        return employee;
    }
}
