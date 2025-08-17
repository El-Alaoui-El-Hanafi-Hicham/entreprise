package com.enterprise.controller;

import com.enterprise.service.BatchImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {
    
    private final BatchImportService batchImportService;
    
    private static final String UPLOAD_DIR = "uploads/";
    
    @PostMapping("/upload/departments")
    public ResponseEntity<Map<String, Object>> uploadDepartments(@RequestParam("file") MultipartFile file) {
        return processFileUpload(file, "departments");
    }
    
    @PostMapping("/upload/employees")
    public ResponseEntity<Map<String, Object>> uploadEmployees(@RequestParam("file") MultipartFile file) {
        return processFileUpload(file, "employees");
    }
    
    private ResponseEntity<Map<String, Object>> processFileUpload(MultipartFile file, String type) {
        try {
            // Validate file
            if (file.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "File is empty");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (!file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Only CSV files are allowed");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Save file
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            
            log.info("File uploaded: {} for type: {}", fileName, type);
            
            // Validate CSV format
            if (!batchImportService.validateCsvFormat(filePath.toAbsolutePath().toString(), type)) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Invalid CSV format for " + type);
                Files.deleteIfExists(filePath); // Clean up invalid file
                return ResponseEntity.badRequest().body(response);
            }
            
            // Process file using service
            Map<String, Object> result;
            if ("departments".equals(type)) {
                result = batchImportService.processDepartmentsCsv(filePath.toAbsolutePath().toString());
            } else if ("employees".equals(type)) {
                result = batchImportService.processEmployeesCsv(filePath.toAbsolutePath().toString());
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", "Invalid type: " + type);
                Files.deleteIfExists(filePath);
                return ResponseEntity.badRequest().body(response);
            }
            
            // Clean up uploaded file after processing
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log.warn("Could not delete uploaded file: {}", e.getMessage());
            }
            
            // Return appropriate response based on result
            if (Boolean.TRUE.equals(result.get("success"))) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
            
        } catch (Exception e) {
            log.error("Error processing file upload for type {}: {}", type, e.getMessage(), e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", "Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/status/{jobId}")
    public ResponseEntity<Map<String, String>> getJobStatus(@PathVariable Long jobId) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Job status endpoint - implement if needed");
        return ResponseEntity.ok(response);
    }
}
