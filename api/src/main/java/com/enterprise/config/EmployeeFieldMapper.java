package com.enterprise.config;

import com.enterprise.entity.Employee;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeFieldMapper implements org.springframework.batch.item.file.mapping.FieldSetMapper<Employee> {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public Employee mapFieldSet(FieldSet fieldSet) throws BindException {
        Employee employee = new Employee();
        
        try {
            System.out.println("Processing employee: " + fieldSet.readString("firstName") + " " + fieldSet.readString("lastName"));
            
            employee.setFirstName(fieldSet.readString("firstName"));
            employee.setLastName(fieldSet.readString("lastName"));
            employee.setEmail(fieldSet.readString("email"));
            employee.setJobTitle(fieldSet.readString("job_title"));
            employee.setPhoneNumber(fieldSet.readString("phone_number"));
            
            // Handle date parsing with better error handling
            String hireDateString = fieldSet.readString("hire_date");
            if (hireDateString != null && !hireDateString.trim().isEmpty()) {
                try {
                    Date hireDate = DATE_FORMAT.parse(hireDateString);
                    employee.setHireDate(hireDate);
                } catch (ParseException e) {
                    System.err.println("Error parsing hire date: " + hireDateString + ", using current date instead");
                    employee.setHireDate(new Date()); // fallback to current date
                }
            } else {
                employee.setHireDate(new Date()); // fallback to current date
            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error parsing phone number for employee: " + fieldSet.readString("firstName"));
            employee.setPhoneNumber(String.valueOf(0)); // fallback value
        } catch (Exception e) {
            System.err.println("Error mapping employee fields: " + e.getMessage());
            throw new BindException(employee, "employee");
        }
        
        return employee;
    }
}
