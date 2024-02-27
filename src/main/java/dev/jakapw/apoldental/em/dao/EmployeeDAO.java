package dev.jakapw.apoldental.em.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.jakapw.apoldental.em.dto.EmployeeDTO;
import dev.jakapw.apoldental.em.exception.EmployeeNotFoundException;
import dev.jakapw.apoldental.em.model.Employee;
import dev.jakapw.apoldental.em.repository.EmployeeRepository;

@Service
public class EmployeeDAO {
	
	@Autowired
	EmployeeRepository employeeRepository;

	public Optional<EmployeeDTO> saveNewEmployee(Employee newEmployee) {
		newEmployee.setCreatedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
        Employee savedEmployee = employeeRepository.save(newEmployee);
        return Optional.of(EmployeeDTO.getInstance(savedEmployee));
	}
	
	public List<EmployeeDTO> getAllEmployees() {
		List<EmployeeDTO> allSavedEmployee = employeeRepository
				.findAll()
				.stream()
				.map(em -> EmployeeDTO.getInstance(em))
				.toList();
		return allSavedEmployee;
	}
	
	public Employee saveUpdateEmployee(Employee updateData) throws EmployeeNotFoundException {
		Employee oldEmployee = employeeRepository.findByEmail(updateData.getEmail());
		if (Objects.isNull(oldEmployee)) {
			throw new EmployeeNotFoundException("update failed");
		}
		oldEmployee.setFirstName(updateData.getFirstName());
		oldEmployee.setLastName(updateData.getLastName());
		oldEmployee.setEmail(updateData.getEmail());
		oldEmployee.setPhone(updateData.getPhone());
		oldEmployee.setModifiedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		
		return employeeRepository.save(oldEmployee);
	}
	
	public void deleteEmployee(Long id) throws EmployeeNotFoundException {
		Optional<Employee> savedEmployee = employeeRepository.findById(id);
        if (savedEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee was not saved yet");
        }
        employeeRepository.deleteById(id);
	}
	
	public Employee getEmployee(Long id) throws EmployeeNotFoundException {
		Optional<Employee> savedEmployee = employeeRepository.findById(id);
        if (savedEmployee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee was not saved yet");
        }
        return savedEmployee.get();
	}
}
