package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();
    @GetMapping
    public List<Employee> getAllEmployee() {
        return employees;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addEmployee(@RequestBody Employee employee) {
        if (employees.stream().anyMatch(employeeInList -> employeeInList.getName().equals(employee.getName()))) {
            return "cannot add";
        }
        employee.setId(employees.size());
        employees.add(employee);
        return "added";
    }

    @PostMapping("/search-by-name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Employee findEmployeeByName(@RequestBody String name) {
        return employees.stream().filter(employee -> employee.getName().equals(name)).findFirst().orElse(null);
    }

    @DeleteMapping("/delete-by-name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteEmployee(@RequestBody String name) {
        if (employees.stream().anyMatch(employee -> employee.getName().equals(name))) {
            employees = employees.stream().filter(employee -> !employee.getName().equals(name)).collect(Collectors.toList());
            return "deleted";
        }
        return "Name not found";
    }
}