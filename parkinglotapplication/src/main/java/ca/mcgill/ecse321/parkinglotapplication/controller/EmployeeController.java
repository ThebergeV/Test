package ca.mcgill.ecse321.parkinglotapplication.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotapplication.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
import ca.mcgill.ecse321.parkinglotapplication.service.EmployeeService;


@RestController
public class EmployeeController {

    @Autowired
	private EmployeeService employeeService;

    /**
	 * Gets all employees.
	 *
	 * @return All employees.
	 */
    @GetMapping("/employee")
	public Iterable<EmployeeResponseDto> getAllEmployees() {
		return StreamSupport.stream(employeeService.getAllEmployees().spliterator(), false)
				.map(e -> new EmployeeResponseDto(e))
				.collect(Collectors.toList());
    }

    /**
	 * Gets a specific employee.
	 *
	 * @param id The primary key of the employee to look up.
	 * @return The employee with the given ID.
	 */
    @GetMapping("/employee/{accountId}")
	public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int accountId) {
		Employee employee = employeeService.getEmployeeByID(accountId);
		EmployeeResponseDto responseBody = new EmployeeResponseDto(employee);
		return new ResponseEntity<EmployeeResponseDto>(responseBody, HttpStatus.OK);
	}

/**
	 * @param employeeDto The employee to create.
	 * @return The created employee.
	 */
	@PostMapping("/employee")
	public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto employeeDto) {
		Employee employee = employeeDto.toModel();
		employee = employeeService.createEmployee(employee);
		EmployeeResponseDto responseBody = new EmployeeResponseDto(employee);
		return new ResponseEntity<EmployeeResponseDto>(responseBody, HttpStatus.CREATED);
	}

	 /**
	 * @param email of the employee to delete.
	 * @return The deleted email user 
	 */
	@DeleteMapping("/employee")
	public ResponseEntity<EmployeeResponseDto> deleteEmployee(@RequestBody String email) {
		Employee employee = employeeService.deleteEmployee(email);
		EmployeeResponseDto responseBody = new EmployeeResponseDto(employee);
		return new ResponseEntity<EmployeeResponseDto>(responseBody, HttpStatus.OK);
	}
}
