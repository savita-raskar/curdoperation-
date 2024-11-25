

package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit-employee/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/update-employee/{id}")
    public String updateEmployee(@PathVariable Long id, @Valid @ModelAttribute Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-employee";
        }
        employee.setId(id); // Set ID for the existing record
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
