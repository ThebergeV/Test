
package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;

@SpringBootTest
public class EmployeeTests {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		employeeRepository.deleteAll();
	}
	
	@Test
	public void testEmployee() {

		//Create object
		Employee employee = new Employee();
		 
		String name = "Sparky";
		String email = "spraky@email.com";
		String number = "2008";
		String password = "sparks";

		employee.setName(name);
		employee.setEmail(email);
		employee.setNumber(number);
		employee.setPassword(password);

		//Save Object
		employee = employeeRepository.save(employee);

		//Read object from database
		employee = employeeRepository.findEmployeeByAccountId(employee.getAccountId());

		//tests
		assertNotNull(employee);
		assertEquals(name, employee.getName());
		assertEquals(email, employee.getEmail());
		assertEquals(number, employee.getNumber());
	}
	
	@Test
	void testDeleteEmployee(){
		
		//Create object
		Employee employee = new Employee();
		
		String name = "Sparky";
		String email = "spraky@email.com";
		String number = "2008";
		String password = "sparks";

		employee.setName(name);
		employee.setEmail(email);
		employee.setNumber(number);
		employee.setPassword(password);

		//Save Object
		employee = employeeRepository.save(employee);

		//Delete object from database
		employeeRepository.delete(employee);

		//Assert that database doesn't have object
		assertNull(employeeRepository.findEmployeeByAccountId(employee.getAccountId()));


	}
}

