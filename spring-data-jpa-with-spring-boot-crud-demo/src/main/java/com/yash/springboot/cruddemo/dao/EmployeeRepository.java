package com.yash.springboot.cruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.springboot.cruddemo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// that's it... no neet to write any code LOL!
}
