package dev.jakapw.apoldental.em.dto;

import dev.jakapw.apoldental.em.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	
	public static EmployeeDTO getInstance(Employee employee) {
		EmployeeDTO empDTO = EmployeeDTO.builder()
        		.id(employee.getId())
        		.email(employee.getEmail())
        		.firstName(employee.getFirstName())
        		.lastName(employee.getLastName())
        		.phone(employee.getPhone())
        		.build();
		return empDTO;
	}
}
