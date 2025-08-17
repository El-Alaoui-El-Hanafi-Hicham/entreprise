# Batch CSV Import for Departments and Employees

This implementation provides endpoints to batch import departments and employees from CSV files directly into the database.

## Features

✅ **Department CSV Import**: Upload department data from CSV to database  
✅ **Employee CSV Import**: Upload employee data from CSV to database  
✅ **File Validation**: Validates CSV format and file structure  
✅ **Error Handling**: Comprehensive error handling and logging  
✅ **Automatic Cleanup**: Removes temporary files after processing  
✅ **Transaction Management**: Ensures data consistency with Spring Batch  
✅ **Progress Tracking**: Returns processing statistics  

## API Endpoints

### Upload Departments CSV
```
POST /api/batch/upload/departments
Content-Type: multipart/form-data
Parameter: file (CSV file)
```

### Upload Employees CSV
```
POST /api/batch/upload/employees
Content-Type: multipart/form-data
Parameter: file (CSV file)
```

## CSV File Formats

### Departments CSV Format
```csv
department_name
Human Resources
Information Technology
Finance
Marketing
```

**Required Columns:**
- `department_name`: Name of the department

### Employees CSV Format
```csv
firstName,lastName,email,job_title,phone_number,hire_date
John,Doe,john.doe@company.com,Software Engineer,1234567890,2023-01-15
Jane,Smith,jane.smith@company.com,Project Manager,1234567891,2023-02-20
```

**Required Columns:**
- `firstName`: Employee's first name
- `lastName`: Employee's last name  
- `email`: Employee's email address (must be unique)
- `job_title`: Employee's job title
- `phone_number`: Employee's phone number (numeric)
- `hire_date`: Employee's hire date (format: YYYY-MM-DD)

## Response Format

### Success Response
```json
{
  "success": true,
  "message": "File processed successfully",
  "jobId": 123,
  "status": "COMPLETED",
  "type": "departments",
  "recordsProcessed": 7,
  "recordsRead": 7
}
```

### Error Response
```json
{
  "success": false,
  "error": "Invalid CSV format for departments"
}
```

## Sample Files

Use the provided sample files for testing:
- `sample_departments_fixed.csv`
- `sample_employees_fixed.csv`

## Technical Implementation

### Components
1. **BatchController**: REST endpoints for file upload
2. **BatchImportService**: Core business logic for processing
3. **DepartmentJob**: Spring Batch job for department import
4. **EmployeesBatchConfig**: Spring Batch job for employee import
5. **Field Mappers**: Convert CSV fields to entity objects

### Batch Processing
- Uses Spring Batch framework for robust data processing
- Chunk-based processing (configurable batch sizes)
- Automatic transaction management
- Rollback on errors

### Error Handling
- File validation (empty files, non-CSV files)
- CSV format validation (header structure)
- Data parsing errors with fallback values
- Database constraint violations

## Usage Examples

### Using cURL

**Upload Departments:**
```bash
curl -X POST http://localhost:8080/api/batch/upload/departments \
  -F "file=@sample_departments_fixed.csv"
```

**Upload Employees:**
```bash
curl -X POST http://localhost:8080/api/batch/upload/employees \
  -F "file=@sample_employees_fixed.csv"
```

### Using Postman
1. Create a POST request to the endpoint
2. Set Content-Type to `multipart/form-data`  
3. Add a form field named `file` and select your CSV file
4. Send the request

## Database Tables

The system expects these database tables to exist:

### Department Table
```sql
CREATE TABLE department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(255) UNIQUE
);
```

### Employee Table
```sql
CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    job_title VARCHAR(255),
    phone_number INT,
    hire_date DATE,
    department_id BIGINT,
    -- other fields...
    FOREIGN KEY (department_id) REFERENCES department(id)
);
```

## Troubleshooting

### Common Issues

1. **"Invalid CSV format"**: Check that your CSV headers match exactly
2. **"Date parsing error"**: Ensure dates are in YYYY-MM-DD format
3. **"Duplicate email"**: Employee emails must be unique
4. **"File upload failed"**: Check file permissions and disk space

### Logs
Check application logs for detailed error information:
```
DEBUG com.enterprise.batch - Processing employee: John Doe
ERROR com.enterprise.service - Error parsing hire date: 2023/01/15
```

## Configuration

Batch processing settings can be configured in the respective batch configuration classes:
- Chunk size (default: 10 for departments, 5 for employees)
- Transaction timeout
- Skip limits for errors

## Next Steps

- Add support for updating existing records
- Implement department-employee relationship import
- Add CSV export functionality
- Create bulk validation endpoints
- Add scheduled batch jobs
