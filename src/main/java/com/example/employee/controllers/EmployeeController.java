package com.example.employee.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	
	 @Autowired
	 EmployeeRepository employeeRepository;
	 
	 @PostMapping("/employees")
	 public String createNewEmployee(@RequestBody Employee employee)
	 {
		 employeeRepository.save(employee);
		 return "Employee created in database";
		 
	 }
	 
	 @GetMapping("/employees")
	 public ResponseEntity<List<Employee>> getAllEmployees() {
	     // Create a List to store employees
	     List<Employee> empList = new ArrayList<>();

	     // Retrieve all employees from the repository and add them to empList
	     employeeRepository.findAll().forEach(empList::add);

	     // Create a ResponseEntity with the employee list and return it
	     return new ResponseEntity<>(empList, HttpStatus.OK);
	 }
	 @GetMapping("/employees/{empid}")
	 public ResponseEntity<Employee> getEmployeeId(@PathVariable long empid)
	 {
		 System.out.println("1");
		 Optional<Employee> emp=employeeRepository.findById(empid);
		 System.out.println("2");
		 if(emp.isPresent())
		 {
			 System.out.println("3");
			 
			 return new ResponseEntity<>(emp.get(),HttpStatus.FOUND);
		 }
		 else
		 {
			 System.out.println("4");
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 
		 
	 }
	 
	 @PutMapping("employees/{empid}")
	 public String updateEmployeeById(@PathVariable long empid, @RequestBody Employee employee)
	 {
		 Optional<Employee> emp=employeeRepository.findById(empid);
		 if(emp.isPresent())
		 {
			 Employee existEmp=emp.get();
			 existEmp.setEmp_age(employee.getEmp_age());
			 existEmp.setEmp_city(employee.getEmp_city());
			 existEmp.setEmp_name(employee.getEmp_name());
			 existEmp.setEmp_salary(employee.getEmp_salary());
			 employeeRepository.save(existEmp);
			 return "Employee Details against Id " + empid + " updated";
		 }
		 else 
		 {
			return "Employee Details doens't exist for empid "+empid; 
		 }
	 }
	 
	 @DeleteMapping("/employees/{empid}")
	 public String deleteEmployee(@PathVariable long empid)
	 {
		 employeeRepository.deleteById(empid);
		 return "Employee deleted in database";
	 }
	 
////
	
	     // Retrieve all employees from the repository and add them to empList
//	 @GetMapping("/employees/{empid}")
//	 public ResponseEntity<Employee> getEmployeeById(@PathVariable long empid) {
//	     Optional<Employee> emp = employeeRepository.findById(empid);
//	     System.out.println("1");
//
//	     if (emp.isPresent()) {
//	         return new ResponseEntity<>(emp.get(), HttpStatus.FOUND);
//	     } else {
//	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	     }
//	 }

}
