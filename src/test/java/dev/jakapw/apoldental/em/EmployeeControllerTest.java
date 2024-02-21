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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void createAnEmployee() throws Exception {
        Employee employee = Employee.builder()
                .firstName("Jaka")
                .lastName("Pratama")
                .email("jp@gmail.com")
                .password("jaka123")
                .createdAt(new Timestamp(Calendar.getInstance().getTime().getTime()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(employee);

        this.mockMvc.perform(post("http://localhost:8080/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

//    @Test
//    void getAllEmployeesTest() throws Exception {
//        Result
//    	this.mockMvc.perform(get("/employee"))
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
}

