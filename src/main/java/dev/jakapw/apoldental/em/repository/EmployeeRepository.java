package dev.jakapw.apoldental.em.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jakapw.apoldental.em.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	public Employee findByEmail(String emailAddress);
}
