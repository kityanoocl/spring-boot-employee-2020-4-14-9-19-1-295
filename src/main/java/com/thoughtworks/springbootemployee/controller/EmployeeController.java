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
    private int uniqueId = 0;
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
        employee.setId(uniqueId++);
        employees.add(employee);
        return "added";
    }

    @PostMapping("/search-by-name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> findEmployeeByName(@RequestBody String name) {
        return employees.stream().filter(employee -> employee.getName().equals(name)).collect(Collectors.toList());
    }

    @PostMapping("/search-by-id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> findEmployeeById(@RequestBody Integer id) {
        return employees.stream().filter(employee -> employee.getId() == id).collect(Collectors.toList());
    }

    @PostMapping("/search-by-age")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> findEmployeeByAge(@RequestBody Integer age) {
        return employees.stream().filter(employee -> employee.getAge() == age).collect(Collectors.toList());
    }

    @PostMapping("/search-by-gender")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Employee> findEmployeeByGender(@RequestBody String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    @DeleteMapping("/delete-by-name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteEmployeeByName(@RequestBody String name) {
        if (employees.stream().anyMatch(employee -> employee.getName().equals(name))) {
            employees = employees.stream().filter(employee -> !employee.getName().equals(name)).collect(Collectors.toList());
            return "deleted";
        }
        return "Name not found";
    }

    @DeleteMapping("/delete-by-id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteEmployeeById(@RequestBody Integer id) {
        if (employees.stream().anyMatch(employee -> employee.getId() == id)) {
            employees = employees.stream().filter(employee -> employee.getId() != id).collect(Collectors.toList());
            return "deleted";
        }
        return "ID not found";
    }

    @DeleteMapping("/delete-by-age")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteEmployeeByAge(@RequestBody Integer age) {
        if (employees.stream().anyMatch(employee -> employee.getAge() == age)) {
            employees = employees.stream().filter(employee -> employee.getAge() != age).collect(Collectors.toList());
            return "deleted";
        }
        return "Age not found";
    }

    @DeleteMapping("/delete-by-gender")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteEmployeeByGender(@RequestBody String gender) {
        if (employees.stream().anyMatch(employee -> employee.getGender().equals(gender))) {
            employees = employees.stream().filter(employee -> !employee.getGender().equals(gender)).collect(Collectors.toList());
            return "deleted";
        }
        return "Gender not found";
    }

    @PutMapping("/change-info-by-id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String changeEmployeeInfoById(@RequestBody Employee employee) {
        if (employees.stream().anyMatch(employeeInList -> employeeInList.getId() == employee.getId())) {
            Employee employeeToChange = employees.stream().filter(employeeInList -> employeeInList.getId() == employee.getId()).findFirst().orElse(null);
            employeeToChange.setAge(employee.getAge());
            employeeToChange.setName(employee.getName());
            employeeToChange.setGender(employee.getGender());
            return "changed";
        }
        return "ID not found";
    }
}