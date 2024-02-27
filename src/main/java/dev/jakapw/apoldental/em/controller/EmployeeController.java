package dev.jakapw.apoldental.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jakapw.apoldental.em.dao.EmployeeDAO;
import dev.jakapw.apoldental.em.dto.EmployeeDTO;
import dev.jakapw.apoldental.em.model.Employee;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping
    public ResponseEntity<String> getAllEmployees() throws Exception {
    	ObjectMapper objectMapper = new ObjectMapper();
    	ByteArrayOutputStream bodyJson = new ByteArrayOutputStream();
    	
    	objectMapper.writeValue(bodyJson, employeeDAO.getAllEmployees().toArray());
    	
    	ResponseEntity<String> response = ResponseEntity.ok(new String(bodyJson.toByteArray()));
        return response;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) throws Exception {
    	ResponseEntity<EmployeeDTO> response = ResponseEntity.ok(EmployeeDTO.getInstance(employeeDAO.getEmployee(id)));
    	return response;
    }
    

    @PostMapping
    public ResponseEntity<Long> saveNewEmployee(@RequestBody Employee newEmployee) {
    	Optional<EmployeeDTO> employee = employeeDAO.saveNewEmployee(newEmployee);
    	ResponseEntity<Long> response;
    	if (employee.isPresent()) {
    		response = ResponseEntity.status(HttpStatus.CREATED).body(employee.get().getId());
    	} else {
    		response = ResponseEntity.status(400).header("errorMsg", "Failed to save employee").body(null);
    	}
    	return response;
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody Employee newData, @PathVariable Long id) throws Exception {
    	Employee updatedEmployee = employeeDAO.saveUpdateEmployee(newData);
    	return ResponseEntity.ok(EmployeeDTO.getInstance(updatedEmployee));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) throws Exception {
        employeeDAO.deleteEmployee(id);
    }
}
