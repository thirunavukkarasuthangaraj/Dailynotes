package com.thiru.daliyworklog.daliyworklog.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Add auto-generation strategy
	private Integer employeeId;

	@Column(nullable = false, length = 100) // Add constraints
	private String name;

	@Column(unique = true, nullable = false) // Email should be unique and required
	private String email;

	@Column(name = "phone_number", length = 20) // Specify column name for phone
	private String phoneNumber;

	@Column(nullable = false) // Department should be required
	private String department;

	private String designation;

	@Column(nullable = false) // Salary should be required
	private Integer salary;

	@Column(name = "years_of_experience") // Consistent naming
	private Integer yearsOfExperience;

	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;

	@Column(name = "status")  
	private String status;

	@Column(name = "address")  
	private String address;

	@Column(name = "gender")  
	private String gender;

	@Column(name = "skills")
	private String skills;

	@Column(name = "date_of_birth")
	private LocalDate dob;

	@Column(name = "manager_id")
	private int manager;
	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Address> addresses = new ArrayList<>();


	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}


	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
 

 

}
