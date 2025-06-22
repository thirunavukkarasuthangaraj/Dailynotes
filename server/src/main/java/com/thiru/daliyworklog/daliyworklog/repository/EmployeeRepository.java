package com.thiru.daliyworklog.daliyworklog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thiru.daliyworklog.daliyworklog.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	@Query("SELECT e FROM Employee e WHERE e.status = 'Active'")
	List<Employee> getActiveList();
	 


}
