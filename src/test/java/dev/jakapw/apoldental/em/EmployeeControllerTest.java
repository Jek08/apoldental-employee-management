package dev.jakapw.apoldental.em;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import dev.jakapw.apoldental.em.controller.EmployeeController;
import dev.jakapw.apoldental.em.model.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class EmployeeControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    EmployeeController employeeController;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
    }

    @Test
    void SaveNewEmployee_ShouldReturnStatusCreated() throws Exception {
        Employee employee = Employee.builder()
        		.id(45L)
                .firstName("Jaka")
                .lastName("Pratama")
                .email("jp1@gmail.com")
                .password("jaka123")
                .phone("08899821")
                .createdAt(new Timestamp(Calendar.getInstance().getTime().getTime()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer();

        String requestJson = ow.writeValueAsString(employee);

        this.mockMvc.perform(post("http://localhost:8080/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }
    
    @Test
    void GetAllEmployees_ShouldReturnStatusOK() throws Exception {
    	Employee employee = Employee.builder()
    			.id(32L)
                .firstName("Jaka")
                .lastName("Pratama")
                .email("jpsdfg2@gmail.com")
                .password("jaka123")
                .phone("0889982213")
                .createdAt(new Timestamp(Calendar.getInstance().getTime().getTime()))
                .build();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	ObjectWriter ow = mapper.writer();
    	String requestJson = ow.writeValueAsString(employee);
    	
    	this.mockMvc.perform(post("http://localhost:8080/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));
    	
    	this.mockMvc.perform(get("http://localhost:8080/api/employee"))
    			.andExpect(status().isOk());
    }
    
    @Test
    void GetEmployeeById_ShouldReturnStatusOk() throws Exception {
    	Employee employee = Employee.builder()
    			.id(12L)
                .firstName("Jaka")
                .lastName("Pratama")
                .email("jp25d4@gmail.com")
                .password("jaka123")
                .phone("088998284")
                .createdAt(new Timestamp(Calendar.getInstance().getTime().getTime()))
                .build();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	ObjectWriter ow = mapper.writer();
    	String requestJson = ow.writeValueAsString(employee);
    	
    	this.mockMvc.perform(post("http://localhost:8080/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));
    	
    	this.mockMvc.perform(get("http://localhost:8080/api/employee/{id}", employee.getId().toString()))
		.andExpect(status().isOk());
    }
    
    @Test
    void UpdateEmployee_ShouldReturnStatusOk() throws Exception {
    	Employee employee = Employee.builder()
    			.id(88L)
                .firstName("Jaka")
                .lastName("Pratama")
                .email("jp2@gmail.com")
                .password("jaka123")
                .phone("08899822")
                .createdAt(new Timestamp(Calendar.getInstance().getTime().getTime()))
                .build();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	ObjectWriter ow = mapper.writer();
    	String requestJson = ow.writeValueAsString(employee);
    	
    	this.mockMvc.perform(post("http://localhost:8080/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));
    	
    	employee.setPassword("akaj123");
    	employee.setModifiedAt(new Timestamp(Calendar.getInstance().getTime().getTime()));
    	
    	requestJson = ow.writeValueAsString(employee);
    	
    	this.mockMvc.perform(put("http://localhost:8080/api/employee/{id}", employee.getId().toString())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(requestJson))
    	.andExpect(status().isOk());
    }
    
    @Test
    void DeleteEmployee_ShouldReturnStatusOk() throws Exception{
    	Employee employee = Employee.builder()
    			.id(44L)
                .firstName("Jaka")
                .lastName("Pratama")
                .email("jp3@gmail.com")
                .password("jaka123")
                .phone("08899823")
                .createdAt(new Timestamp(Calendar.getInstance().getTime().getTime()))
                .build();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	ObjectWriter ow = mapper.writer();
    	String requestJson = ow.writeValueAsString(employee);
    	
    	this.mockMvc.perform(post("http://localhost:8080/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));
    	
    	this.mockMvc.perform(delete("http://localhost:8080/api/employee/{id}", employee.getId().toString()))
    	.andExpect(status().isOk());
    }
}

