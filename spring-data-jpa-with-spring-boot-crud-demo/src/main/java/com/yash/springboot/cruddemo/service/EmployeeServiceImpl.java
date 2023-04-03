package com.yash.springboot.cruddemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.springboot.cruddemo.dao.EmployeeRepository;
import com.yash.springboot.cruddemo.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository theemployeeRepository) {
		employeeRepository = theemployeeRepository;
	}
	
	@Override
	public List<Employee> findAll() {
		
		return employeeRepository.findAll();
	}

	@Override
	public Employee findById(int theId) { 
		
		Optional<Employee> result = employeeRepository.findById(theId);
		Employee theemployee = null;		
		if(result.isPresent()) {
			theemployee =  result.get();
		}
		
//		  else { // we didn't find employee throw new EmployeeNotFoundException(theId);
//		  }
		 
		return theemployee;
		
	}

	@Override
	public Employee save(Employee theEmployee) {
		
		return employeeRepository.save(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		
		employeeRepository.deleteById(theId);
	}

}
