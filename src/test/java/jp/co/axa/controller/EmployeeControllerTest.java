package jp.co.axa.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import jp.co.axa.apidemo.controllers.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.UserException;
import jp.co.axa.apidemo.services.EmployeeService;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployees() {
        // Mocking service behavior
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John Doe", 1000,"EEE"));
        employees.add(new Employee(2L, "Jane Smith", 2000,"IT"));
        when(employeeService.retrieveEmployees()).thenReturn(employees);

        // Calling controller method
        List<Employee> result = employeeController.getEmployees();

        // Verifying service method is called
        verify(employeeService, times(1)).retrieveEmployees();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test(expected = UserException.class)
    public void testGetEmployeeWithNullId() {
        employeeController.getEmployee(null);
    }

    @Test(expected = UserException.class)
    public void testGetEmployeeWithZeroId() {
        employeeController.getEmployee(0L);
    }

    @Test
    public void testSaveEmployee() {
        // Creating employee
        Employee employee = new Employee(1L, "John Doe", 1000,"IT");

        // Calling controller method
        employeeController.saveEmployee(employee);

        // Verifying service method is called
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test(expected = UserException.class)
    public void testDeleteEmployeeWithNullId() {
        employeeController.deleteEmployee(null);
    }

    @Test(expected = UserException.class)
    public void testDeleteEmployeeWithZeroId() {
        employeeController.deleteEmployee(0L);
    }

    @Test
    public void testDeleteEmployee() {
        // Calling controller method
        employeeController.deleteEmployee(1L);

        // Verifying service method is called
        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test(expected = UserException.class)
    public void testUpdateEmployeeWithNullId() {
        employeeController.updateEmployee(new Employee(), null);
    }

    @Test(expected = UserException.class)
    public void testUpdateEmployeeWithZeroId() {
        employeeController.updateEmployee(new Employee(), 0L);
    }

    @Test
    public void testUpdateEmployeeNotFound() {
        // Mocking service behavior to return null when searching for employee
        when(employeeService.getEmployee(anyLong())).thenReturn(null);

        // Calling controller method
        try {
            employeeController.updateEmployee(new Employee(), 1L);
        } catch (UserException e) {
            // Verifying exception details
            assertEquals("Employee not found", e.getReason());
            assertEquals(HttpStatus.NO_CONTENT, e.getStatus());
        }
    }

    @Test
    public void testUpdateEmployee() {
        // Mocking service behavior
        Employee existingEmployee = new Employee(1L, "John Doe", 1000,"EEE");
        when(employeeService.getEmployee(1L)).thenReturn(existingEmployee);

        // Creating employee for update
        Employee updatedEmployee = new Employee(1L, "Updated Name", 2000,"IT");

        // Calling controller method
        employeeController.updateEmployee(updatedEmployee, 1L);

        // Verifying service method is called
        verify(employeeService, times(1)).updateEmployee(updatedEmployee);
    }
}