package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.UserException;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.log4j.Log4j;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@ApiOperation(value = "Returns Employee Details")
	@GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getEmployees() {
		
		List<Employee> employees = employeeService.retrieveEmployees();
		return employees;
	}

	@ApiOperation(value = "Returns Employee Details by id")
	@GetMapping(value = "/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {

		if (Objects.isNull(employeeId) || employeeId == 0l) {
			throw new UserException("EmployeeId should be provided", HttpStatus.BAD_REQUEST);
		}
		return employeeService.getEmployee(employeeId);
	}

	@ApiOperation(value = "Provides the ability to create Employee Details")
	@PostMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public void saveEmployee(@RequestBody Employee employee) {
		employeeService.saveEmployee(employee);
		System.out.println("Employee Saved Successfully");
	}

	@ApiOperation(value = "Provides the ability to Delete Employee Details by Id")
	@DeleteMapping(value = "/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {

		if (Objects.isNull(employeeId) || employeeId == 0l) {
			throw new UserException("EmployeeId should be provided", HttpStatus.BAD_REQUEST);
		}
		employeeService.deleteEmployee(employeeId);
		System.out.println("Employee Deleted Successfully");
	}

	@ApiOperation(value = "Provides the ability to Updates Employee Details")
	@PutMapping(value = "/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateEmployee(@RequestBody Employee employee, @PathVariable(name = "employeeId") Long employeeId) {

		if (Objects.isNull(employeeId) || employeeId == 0l) {
			throw new UserException("EmployeeId should be provided", HttpStatus.BAD_REQUEST);
		}
		
		Employee emp = employeeService.getEmployee(employeeId);
		if (emp != null) {
			employeeService.updateEmployee(employee);
		} else {
			throw new UserException("Employee not found", HttpStatus.NO_CONTENT);
		}

	}

}
