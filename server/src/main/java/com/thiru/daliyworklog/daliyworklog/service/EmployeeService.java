package com.thiru.daliyworklog.daliyworklog.service;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.thiru.daliyworklog.daliyworklog.model.Employee;
import com.thiru.daliyworklog.daliyworklog.repository.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateEmployee(Integer id, Employee employee) {
        return employeeRepository.findById(id).map(e -> {
            e.setName(employee.getName());
            e.setDepartment(employee.getDepartment());
            e.setSalary(employee.getSalary());
            e.setYearsOfExperience(employee.getYearsOfExperience());
            return employeeRepository.save(e);
        });
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    // Pagination with Stream API
  public Map<String, Object> getEmployeesPaginated(int page, int size, String department, Double fromSalary, Double toSalary, 
		  String[] skill, String gender, String city,   String state,   String country) {
    List<Employee> allEmployees = employeeRepository.getActiveList();

    Set<String> requiredSkills = skill != null ?
            Arrays.stream(skill).collect(Collectors.toSet()) :
            Collections.emptySet();

    
 
    
    // Apply filters before pagination
    List<Employee> filtered = allEmployees.stream()
            .filter(emp -> emp.getYearsOfExperience() >= 5)
            .filter(e -> fromSalary == null || e.getSalary() >= fromSalary)
            .filter(e -> toSalary == null || e.getSalary() <= toSalary)
            .filter(e -> department == null || e.getDepartment().equalsIgnoreCase(department))
            .filter(e -> gender == null || e.getGender().equalsIgnoreCase(gender))

            .filter(e -> {
                if (requiredSkills.isEmpty()) return true;
                return requiredSkills.stream().allMatch(e.getSkills()::contains);
            })
            
            .filter(e -> {
                if (city == null && state == null && country == null) return true;
                return e.getAddresses().stream().anyMatch(addr ->
                        (city == null || addr.getCity().equalsIgnoreCase(city)) &&
                        (state == null || addr.getState().equalsIgnoreCase(state)) &&
                        (country == null || addr.getCountry().equalsIgnoreCase(country))
                );
            })
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()
                    .thenComparing(Comparator.comparing(Employee::getYearsOfExperience).reversed())
                    .thenComparing(Comparator.comparing(Employee::getName)))
            .collect(Collectors.toList());

    int total = filtered.size();
    int start = page * size;
    int end = Math.min(start + size, total);

    List<Employee> paged = (start < total) ? filtered.subList(start, end) : Collections.emptyList();

    Map<String, List<Employee>> groupedByDept = paged.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));

    Map<String, Object> response = new HashMap<>();
    response.put("data", groupedByDept);
    response.put("currentPage", page);
    response.put("pageSize", size);
    response.put("totalElements", total);
    response.put("totalPages", (int) Math.ceil((double) total / size));
    return response;
}


    // Multiple-field sorting
    public List<Employee> getEmployeesSorted() {
        List<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees.stream()
                .sorted(
                        Comparator.comparing(Employee::getDepartment)
                                .thenComparing(Comparator.comparing(Employee::getSalary).reversed())
                )
                .collect(Collectors.toList());
    }

    // Filtering
    public List<Employee> filterEmployees(String department, Integer minSalary, Integer minExperience) {
        return employeeRepository.findAll().stream()
                .filter(e -> department == null || e.getDepartment().equalsIgnoreCase(department))
                .filter(e -> minSalary == null || e.getSalary() >= minSalary)
                .filter(e -> minExperience == null || e.getYearsOfExperience() >= minExperience)
                .collect(Collectors.toList());
    }
    
    // Filter department 

	public Map<String, List<Employee>>  groupbyDepartment() {
		List<Employee> allEmployees = employeeRepository.getActiveList();
		 return allEmployees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
	}
	
    // Filter department 

	public Map<String, Optional<Employee>>  groupbyDepartmenthighsalary() {
		List<Employee> allEmployees = employeeRepository.getActiveList();
 		Map<String, Optional<Employee>> topSalariess =   allEmployees.stream()
				.collect(Collectors.groupingBy
						(Employee::getDepartment,
				         Collectors.maxBy(Comparator.comparingInt(Employee::getSalary))
				         ));
		 
		 return topSalariess;
	
	}
	
	
	
}

