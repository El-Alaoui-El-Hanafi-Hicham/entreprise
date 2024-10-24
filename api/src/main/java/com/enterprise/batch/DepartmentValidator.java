package com.enterprise.batch;

import com.enterprise.entity.Department;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Department.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;
        ValidationUtils.rejectIfEmpty(errors,"department_name.empty","Department Name Can't be Empty");
        // Additional custom validation rules
        if (department.getDepartment_name().length() > 50) {
            errors.rejectValue("departmentName", "departmentName.tooLong", "Department name is too long");
        }
    }
}
