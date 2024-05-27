package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> retrieveEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	public Employee getEmployee(Long employeeId) {
		Optional<Employee> optEmp = null;
		try {
			optEmp = employeeRepository.findById(employeeId);
		} catch (Throwable t) {
			logger.error("Unable to fetch record for Employee [{}]", employeeId);
			t.printStackTrace();
		}
		return optEmp.get();
	}

	public void saveEmployee(Employee employee) {
		try {
			employeeRepository.save(employee);
			logger.info("Employee records saved.");
		} catch (Throwable t) {
			logger.error("Unable to save records for Employee");
			t.printStackTrace();
		}
	}

	public void deleteEmployee(Long employeeId) {
		try {
			employeeRepository.deleteById(employeeId);
			logger.info("Employee record deleted for id [{}]", employeeId);
		} catch (Throwable t) {
			logger.error("Unable to delete the records for Employee [{}]", employeeId);
			t.printStackTrace();
		}
	}

	public void updateEmployee(Employee employee) {
		try {
			employeeRepository.save(employee);
			logger.info("Employee records updated");
		} catch (Throwable t) {
			logger.error("Unable to update records for Employee");
			t.printStackTrace();
		}
	}
}