package ca.mcgill.ecse321.parkinglotapplication.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDateTime;

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

import ca.mcgill.ecse321.parkinglotapplication.dao.BillRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotapplication.dto.BillRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.BillResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;

// Start the app but using a random port to avoid conflicts
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
public class BillIntegrationTests {

    // Stores state to be shared between tests
	private class TestFixture {

		private int id;

		public static final int INVALID_TRANSACTION_ID = Integer.MAX_VALUE;

		public static final double BILL_PRICE = 18;

        public static final Date BILL_DATE = Date.valueOf(LocalDateTime.now().toLocalDate());;

        public static final CustomerType BILL_CUSTOMER_TYPE = CustomerType.NonAccountHolder;

		public static final boolean BILL_IS_PAID = false;

		public int getTransactionId() {
			return id;
		}

		public void setTransactionId(int id) {
			this.id = id;
		}
	}

    private TestFixture fixture;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private TestRestTemplate client;

    @BeforeAll
	public void setupTestFixture() {
		this.fixture = new TestFixture();
	}

    @BeforeAll
	@AfterAll
	public void clearDatabase() {
		employeeRepository.deleteAll();
		billRepository.deleteAll();
	}

	Employee rick = new Employee("Rick", "rick@mail.com", "519-090-0099", "password123");
	

	@Test
	@Order(0)
	public void testCreateBill() {
		employeeRepository.save(rick);

		double price = 18;
        Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
        CustomerType customerType = CustomerType.NonAccountHolder;
        boolean isPaid = false;

		BillRequestDto request = new BillRequestDto();
		request.setprice(price);
		request.setdate(date);
		request.setIsPaid(isPaid);
		request.setCustomerType(customerType);
		request.setAccountHolder(rick);

		ResponseEntity<BillResponseDto> response = client.postForEntity("/bill", request, BillResponseDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(price, response.getBody().getPrice());
        assertEquals(date, response.getBody().getDate());
        assertEquals(isPaid, response.getBody().getIsPaid());
        assertEquals(customerType, response.getBody().getCustomerType());
		assertEquals(rick, response.getBody().getAccountHolder());
		assertTrue(response.getBody().getTransactionId() >= 1, "Response ID is at least 1.");

		// Save the ID so that later tests can use it
		fixture.setTransactionId(response.getBody().getTransactionId());
	}

    @Test
	@Order(1)
	public void testGetBill() {
		int id = fixture.getTransactionId();

		ResponseEntity<BillResponseDto> response = client.getForEntity("/bill/" + id, BillResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(TestFixture.BILL_PRICE, response.getBody().getPrice());
		assertEquals(TestFixture.BILL_DATE, response.getBody().getDate());
		assertEquals(TestFixture.BILL_CUSTOMER_TYPE, response.getBody().getCustomerType());
		assertEquals(TestFixture.BILL_IS_PAID, response.getBody().getIsPaid());
		assertEquals(id, response.getBody().getTransactionId());
	}

	@Test
	@Order(2)
	public void testCreateInvalidBill() {
		BillRequestDto request = new BillRequestDto();

		ResponseEntity<String> response = client.postForEntity("/bill", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(3)
	public void testGetInvalidBill() {
		ResponseEntity<String> response = client.getForEntity("/bill/" + TestFixture.INVALID_TRANSACTION_ID, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Bill not found", response.getBody());
	}

	@Test
	@Order(4)
	public void testUpdateBill(){

		int id = fixture.getTransactionId();
		double price = 18;
        Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
        CustomerType customerType = CustomerType.NonAccountHolder;
        boolean isPaid = true;

		BillRequestDto request = new BillRequestDto();
		request.setprice(price);
		request.setdate(date);
		request.setIsPaid(isPaid);
		request.setCustomerType(customerType);
		request.setAccountHolder(rick);

		client.put("bill/" + id, request);
		

		ResponseEntity<BillResponseDto> response = client.getForEntity("/bill" + id, BillResponseDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(price, response.getBody().getPrice());
        assertEquals(date, response.getBody().getDate());
        assertEquals(isPaid, response.getBody().getIsPaid());
        assertEquals(customerType, response.getBody().getCustomerType());
		assertEquals(rick, response.getBody().getAccountHolder());
		assertEquals(id, response.getBody().getTransactionId());
	}
}
