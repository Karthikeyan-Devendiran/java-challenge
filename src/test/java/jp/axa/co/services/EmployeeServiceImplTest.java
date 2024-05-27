package jp.axa.co.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveEmployees() {
        // Mocking repository behavior
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John Doe", 1000, "IT"));
        employees.add(new Employee(2L, "Jane Smith", 2000, "IT"));
        when(employeeRepository.findAll()).thenReturn(employees);

        // Calling service method
        List<Employee> result = employeeService.retrieveEmployees();

        // Verifying repository method is called
        verify(employeeRepository, times(1)).findAll();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    public void testGetEmployee() {
        // Mocking repository behavior
        Employee employee = new Employee(1L, "John Doe", 1000, "IT");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Calling service method
        Employee result = employeeService.getEmployee(1L);

        // Verifying repository method is called
        verify(employeeRepository, times(1)).findById(1L);

        // Assertions
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testSaveEmployee() {
        // Creating employee
        Employee employee = new Employee(1L, "John Doe", 1000, "IT");

        // Calling service method
        employeeService.saveEmployee(employee);

        // Verifying repository method is called
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee() {
        // Calling service method
        employeeService.deleteEmployee(1L);

        // Verifying repository method is called
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateEmployee() {
        // Creating employee
        Employee employee = new Employee(1L, "John Doe", 1000, "IT");

        // Mocking repository behavior
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Calling service method
        employeeService.updateEmployee(employee);

        // Verifying repository method is called
        verify(employeeRepository, times(1)).save(employee);
    }
}