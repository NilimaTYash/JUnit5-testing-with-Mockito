package com.yash.springboot.cruddemo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.yash.springboot.cruddemo.entity.Employee;
import com.yash.springboot.cruddemo.rest.EmployeeRestController;
import com.yash.springboot.cruddemo.service.EmployeeNotFoundException;
import com.yash.springboot.cruddemo.service.EmployeeService;
import com.yash.springboot.cruddemo.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(EmployeeRestController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeServiceImpl employeeService;

	@InjectMocks
	private EmployeeRestController employeeRestController;

	Employee emp1 = null;
	Employee emp2 = null;
	Employee emp3 = null;
	
	@BeforeEach
	public void setUp() {
		
		 emp1 = new Employee(8, "Nilima", "thigale", "nilima@gmail.com");
		 emp2 = new Employee(11, "Pratiksha", "Sutar", "pratiksha@gmail.com");
		 emp3 = new Employee(12, "rohini", "tambe", "rohini@gmail.com");
		
	}
	
	

	@Test
	public void findAll_success() throws Exception {

		List<Employee> records = new ArrayList<>(Arrays.asList(emp1, emp2, emp3));

		given(employeeService.findAll()).willReturn(records);

		mockMvc.perform(get("/api/employees")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].firstName", is("Nilima")));

	}

	@Test
	public void findById_success() throws Exception {

		given(employeeService.findById(emp1.getId())).willReturn(emp1);

		mockMvc.perform(get("/api/employees/"+emp1.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(emp1.getFirstName())));

	}

	
//	  @Test public void findById_EmpNotFound() throws Exception {
//	  
//	  Mockito.doThrow(new EmployeeNotFoundException(25)).when((employeeService).findById(25));
//	  // Mockito.when(employeeService.findById(emp1.getId())).thenReturn(Optional.of(emp1)); 
//	  
//	  // given(employeeService.findById(25)).willReturn(Optional.empty());
// 
//	  mockMvc.perform(get("/api/employees/25")
//			  .contentType(MediaType.APPLICATION_JSON)) .andExpect(status().isNotFound());
//	  
//	  }
	 

	@Test
	public void addEmployee_success() throws Exception {

		given(employeeService.save(emp1)).willReturn(emp1);

		mockMvc.perform(
				post("/api/addemployees")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(emp1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstName", is(emp1.getFirstName())));
	} 
	
	@Test
    public void deteteEmployeeById_success() throws Exception {

		Mockito.doNothing().when(employeeService).deleteById(emp1.getId());

        mockMvc.perform(delete("/api/delete_employees/"+emp1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        Mockito.verify(employeeService, times(1)).deleteById(emp1.getId());
    }
	

	
	

}
