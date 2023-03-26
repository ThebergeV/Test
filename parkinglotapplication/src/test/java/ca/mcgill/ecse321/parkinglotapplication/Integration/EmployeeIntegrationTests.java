package ca.mcgill.ecse321.parkinglotapplication.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotapplication.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.EmployeeResponseDto;

// Start the app but using a random port to avoid conflicts
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order 
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")

public class EmployeeIntegrationTests {

    // Stores state to be shared between tests
	private class TestFixture {
		public static final int INVALID_ID = Integer.MAX_VALUE;

		public static final String EMPLOYEE_NAME = "Rick";

        public static final String EMPLOYEE_EMAIL = "Rick@mail.com";

        public static final String EMPLOYEE_NUMBER = "519-090-0099";

		public static final String EMPLOYEE_PASSWORD = "password123";

		private int id;

		public int getAccountId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}

    private TestFixture fixture;

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private EmployeeRepository employeeRepository;

    @BeforeAll
	public void setupTestFixture() {
		this.fixture = new TestFixture();
	}

	@BeforeAll
	@AfterAll
	public void clearDatabase() {
		employeeRepository.deleteAll();
	}


@Test
	@Order(0)
	public void testCreateEmployee() {
		String name = "Rick";
		String password = "password123";
        String number = "519-090-0099";
        String email = "Rick@mail.com";

		EmployeeRequestDto request = new EmployeeRequestDto();
        request.setEmail(email);
		request.setName(name);
		request.setPassword(password);
        request.setNumber(number);

		ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employee", request, EmployeeResponseDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(name, response.getBody().getName());
        assertEquals(password, response.getBody().getPassword());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(number, response.getBody().getNumber());
		assertTrue(response.getBody().getAccountId() >= 1, "Response ID is at least 1.");

		// Save the ID so that later tests can use it
		fixture.setId(response.getBody().getAccountId());
	}

	@Test 
	@Order(2)
	public void testGetEmployee() {
			int id = fixture.getAccountId();
	
			ResponseEntity<EmployeeResponseDto> response = client.getForEntity("/employee/" + id, EmployeeResponseDto.class);
	
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertNotNull(response.getBody());
		    assertEquals(TestFixture.EMPLOYEE_EMAIL, response.getBody().getEmail());
			assertEquals(TestFixture.EMPLOYEE_NAME, response.getBody().getName());
			assertEquals(TestFixture.EMPLOYEE_NUMBER, response.getBody().getNumber());
			assertEquals(TestFixture.EMPLOYEE_PASSWORD, response.getBody().getPassword());
			assertEquals(id, response.getBody().getAccountId());

	}
	@Test
	@Order(3)
	public void testCreateInvalidEmployee() {
		EmployeeRequestDto request = new EmployeeRequestDto();

		ResponseEntity<String> response = client.postForEntity("/employee", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(4)
	public void testGetInvalidEmployee() {
		ResponseEntity<String> response = client.getForEntity("/employee/" + TestFixture.INVALID_ID, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Employee not found.", response.getBody());
	}

	// @Test
	// @Order(4)
	// public void testGetAllEmployees(){
	// 	Iterable<EmployeeResponseDto> response = client.getForEntity("/employee/", EmployeeResponseDto.class );
	// }

}
