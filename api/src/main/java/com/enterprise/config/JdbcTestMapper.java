package com.enterprise.config;

import com.enterprise.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTestMapper implements org.springframework.jdbc.core.RowMapper<com.enterprise.entity.Department> {
    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setDepartmentName(rs.getString("departmentName"));
        System.out.println(department);
        return department;
    }
}
