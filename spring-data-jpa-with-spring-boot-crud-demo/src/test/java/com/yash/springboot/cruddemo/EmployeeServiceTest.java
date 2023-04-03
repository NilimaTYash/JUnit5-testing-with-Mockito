package com.yash.springboot.cruddemo;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.yash.springboot.cruddemo.dao.EmployeeRepository;
import com.yash.springboot.cruddemo.entity.Employee;
import com.yash.springboot.cruddemo.service.EmployeeServiceImpl;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
	
		@MockBean
		private EmployeeRepository employeeRepository;

	    @Autowired
	    private EmployeeServiceImpl employeeService;
	    
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
	    public void findAll_success() {
	    	
	    	List<Employee> employees = new ArrayList();
	    	employees.add(emp1);
	    	employees.add(emp2);
	    	employees.add(emp3);
	    	
	    	given(employeeRepository.findAll()).willReturn(List.of(emp1, emp2, emp3));
	    	
	    	 List<Employee> expected = employeeService.findAll();

	    	 assertThat(expected).isNotNull();
	         assertThat(expected.size()).isEqualTo(3);
	         assertEquals(expected.toString(), employees.toString());
	    	
	    }
	    
	    @Test
	    public void addEmployee_test() {
	    	
	    	when(employeeRepository.save(emp1)).thenReturn(emp1);
	    	
	    	assertEquals(emp1, employeeService.save(emp1));
	    	
	    }
	    
	    @Test
	    public void findById_test() {
	    	
	    	when(employeeRepository.findById(emp1.getId())).thenReturn(Optional.of(emp1));
	    	assertEquals("nilima@gmail.com", employeeService.findById(emp1.getId()).getEmail());
	    }
	    
	    @Test
	    public void deteteEmployeeById_test() {
	    	
	    	employeeService.deleteById(emp1.getId());
	    	verify(employeeRepository, times(1)).deleteById(emp1.getId());;
	    	
	    }
	    

}
