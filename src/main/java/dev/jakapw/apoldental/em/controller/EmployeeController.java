package dev.jakapw.apoldental.em.controller;

import org.apache.logging.log4j.CloseableThreadContext.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jakapw.apoldental.em.dao.EmployeeDAO;
import dev.jakapw.apoldental.em.dto.EmployeeDTO;
import dev.jakapw.apoldental.em.exception.EmployeeNotFoundException;
import dev.jakapw.apoldental.em.model.Employee;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping("/employee")
    public ResponseEntity<String> getAllEmployees() throws Throwable {
    	ObjectMapper objectMapper = new ObjectMapper();
    	ByteArrayOutputStream bodyJson = new ByteArrayOutputStream();
    	
    	objectMapper.writeValue(bodyJson, employeeDAO.getAllEmployees().toArray());
    	
    	ResponseEntity<String> response = ResponseEntity.ok(new String(bodyJson.toByteArray()));
        return response;
    }

    @PostMapping("/employee")
    public ResponseEntity<Long> saveNewEmployee(@RequestBody Employee newEmployee) {
    	Optional<EmployeeDTO> employee = employeeDAO.saveNewEmployee(newEmployee);
    	ResponseEntity<Long> response;
    	if (employee.isPresent()) {
    		response = ResponseEntity.ok(employee.get().getId());
    	} else {
    		response = ResponseEntity.status(400).header("errorMsg", "Failed to save employee").body(null);
    	}
    	return response;
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody Employee updatedEmployee, @PathVariable Long id) throws EmployeeNotFoundException {
//    	Employee employee = employeeDAO.saveUpdateEmployee(updatedEmployee);
//    	return Optional.of(EmployeeDTO.getInstance(employee));
//    }
//
//    @DeleteMapping("/{id}")
//    public  deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
//        
//    }
}
