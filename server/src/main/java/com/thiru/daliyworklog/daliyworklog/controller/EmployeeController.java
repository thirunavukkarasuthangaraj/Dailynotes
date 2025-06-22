package com.thiru.daliyworklog.daliyworklog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thiru.daliyworklog.daliyworklog.ApiResponse;
import com.thiru.daliyworklog.daliyworklog.model.Employee;
import com.thiru.daliyworklog.daliyworklog.service.EmployeeService;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ApiResponse<List<Employee>> getAllEmployees() {
        List<Employee> data = employeeService.getAllEmployees();
        return new ApiResponse<>(true, "Fetched all employees", data);
    }

    @GetMapping("/{id}")
    public ApiResponse<Employee> getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id)
                .map(emp -> new ApiResponse<>(true, "Employee found", emp))
                .orElse(new ApiResponse<>(false, "Employee not found", null));
    }

    @PostMapping
    public ApiResponse<Employee> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        return new ApiResponse<>(true, "Employee created", saved);
    }

    @PutMapping("/{id}")
    public ApiResponse<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee)
                .map(updated -> new ApiResponse<>(true, "Employee updated", updated))
                .orElse(new ApiResponse<>(false, "Employee not found", null));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return new ApiResponse<>(true, "Employee deleted", null);
    }

    // Stream-based custom APIs
    @GetMapping("/paginate")
    public ApiResponse<Map<String, Object>> getEmployeesPaginated(int page, int size,String depatment,Double fromsalary,
    		Double tosalary,  String[] skills,String gender, String city,   String state,   String country)
   {
        Map<String, Object> result = employeeService.getEmployeesPaginated(page, size,depatment,fromsalary, tosalary,skills,gender,city,state,country);
        return new ApiResponse<>(true, "Paginated data", result);
    }

    @GetMapping("/sorted")
    public ApiResponse<List<Employee>> getEmployeesSorted() {
        List<Employee> sorted = employeeService.getEmployeesSorted();
        return new ApiResponse<>(true, "Sorted employees", sorted);
    }
  
    @GetMapping("/groupdepart")
    public ApiResponse<Map<String, List<Employee>>> groupbyDepartment() {
        Map<String, List<Employee>> groupedEmployees = employeeService.groupbyDepartment();
        return new ApiResponse<>(true, "Employees grouped by department", groupedEmployees);
    }
    
    

    @GetMapping("/highsalary")
    public ApiResponse<Map<String, Optional<Employee>>> groupbyDepartmenthighsalry() {
        Map<String, Optional<Employee>> groupedEmployees = employeeService.groupbyDepartmenthighsalary();
        return new ApiResponse<>(true, "Employees grouped by department", groupedEmployees);
    }
    
    
    

    @GetMapping("/filter")
    public ApiResponse<List<Employee>> filterEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer minExperience) {
        List<Employee> filtered = employeeService.filterEmployees(department, minSalary, minExperience);
        return new ApiResponse<>(true, "Filtered employees", filtered);
    }
}