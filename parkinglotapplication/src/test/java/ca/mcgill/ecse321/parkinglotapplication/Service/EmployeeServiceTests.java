package ca.mcgill.ecse321.parkinglotapplication.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
import ca.mcgill.ecse321.parkinglotapplication.service.EmployeeService;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingSpotService;

@SpringBootTest
public class EmployeeServiceTests {

    //create a fake repository
    @Mock
	private EmployeeRepository employeeRepository;
    @Mock
	private ParkingSpotRepository parkingSpotRepository;

    // Autowire employee services with the fake repository 
	@InjectMocks
	private EmployeeService employeeService;
    @InjectMocks
	private ParkingSpotService parkingSpotService;

    @Test
    public void testGetEmployeeByValidId(){
        //set up mock 
        final int id = 1;
		final String name = "Rick";
		final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final Employee rick = new Employee(name, email, number, password);
		when(employeeRepository.findEmployeeByAccountId(id)).thenReturn(rick);

        //call comoponent under test 
        Employee employee = employeeService.getEmployeeByID(id);

        assertNotNull(employee);
        assertEquals(name, employee.getName());
        assertEquals(email, employee.getEmail());
        assertEquals(password, employee.getPassword());
        assertEquals(number, employee.getNumber());

    }

    @Test
	public void testGetEmployeeByInvalidId() {
		final int invalidId = 12;
		when(employeeRepository.findEmployeeByAccountId(invalidId)).thenReturn(null);

		ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> employeeService.getEmployeeByID(invalidId));

		assertEquals("Employee not found.", e.getMessage());
	}

    @Test
	public void CreateInvalidEmployee() {
         //set up mock 
         final String name = "Rick";
         final String password = "password123";
         final String email = "Rick@mail .com";
         final String number = "514-999-8931";
         final Employee rick = new Employee(name, email, number, password);

		ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> employeeService.createEmployee(rick));

		assertEquals("Email cannot contain spaces", e.getMessage());
	}

    @Test
    public void testGetEmployeeByValidEmail(){
         //set up mock 
         final String name = "Rick";
         final String password = "password123";
         final String email = "Rick@mail.com";
         final String number = "514-999-8931";
         final Employee rick = new Employee(name, email, number, password);
         when(employeeRepository.findEmployeeByEmail(email)).thenReturn(rick);

          //call comoponent under test 
        Employee employee = employeeService.getEmployeeByEmail(email);

        assertNotNull(employee);
        assertEquals(name, employee.getName());
        assertEquals(email, employee.getEmail());
        assertEquals(password, employee.getPassword());
        assertEquals(number, employee.getNumber());
    }

    @Test
	public void testCreateValidEmployee() {
		final String name = "Rick";
		final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
		final Employee rick = new Employee(name, email, number, password);
		when(employeeRepository.save(rick)).thenReturn(rick);

		Employee employee = employeeService.createEmployee(rick);
		
		assertNotNull(employee);
        assertEquals(name, employee.getName());
        assertEquals(email, employee.getEmail());
        assertEquals(password, employee.getPassword());
        assertEquals(number, employee.getNumber());

		// personRepo.save() should be called exactly once if it was saved to database
		verify(employeeRepository, times(1)).save(rick);
	}

}