package com.yash.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.springboot.cruddemo.entity.Employee;
import com.yash.springboot.cruddemo.service.EmployeeNotFoundException;
import com.yash.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	
	//quick and dirty: inject employee dao
	@Autowired
	public EmployeeRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	//expose "/employees" and return list of all employees
	@GetMapping("/employees")
	public List<Employee> findAll(){
		
		return employeeService.findAll();
	}
	
	// add mapping for GET  /employees/(employeeId)
	@GetMapping("/employees/{employeeId}")
	public Employee findById(@PathVariable int employeeId) {
		Employee employee = employeeService.findById(employeeId);
		
		if(employee == null) {
			throw new EmployeeNotFoundException(employeeId);
		}
		return employee;
		
	}
	
	// add mappng for POST /employees  - add new employee
	@PostMapping("/addemployees")
	public Employee addEmployee(@RequestBody Employee employee) {
		// also just in case they pass an id in JSOn... set id to 0 
		// this is to force a save of new item... instead of update
		
		employee.setId(0);		
		employeeService.save(employee);
		return employee;
		
	}
	
	
	// add mapping for DELETE /employees/{employeeId} - delete employee
	@DeleteMapping("/delete_employees/{employeeId}")
	public String deteteEmployeeById(@PathVariable int employeeId) {
		
		Employee employee = employeeService.findById(employeeId);
		
		
		  // thorw exception if null 
//		if(employee == null) {
//		 
//		  throw new EmployeeNotFoundException(employeeId);
//		  }
		 
		
		employeeService.deleteById(employeeId);
		
		return "Delete Employee record for ID - "+employeeId;
		
		
	}
	
	
	
	
	
}
