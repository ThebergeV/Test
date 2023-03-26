package ca.mcgill.ecse321.parkinglotapplication.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;

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

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingLotApplicationRepository;
import ca.mcgill.ecse321.parkinglotapplication.dto.ParkingLotApplicationRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.ParkingLotApplicationResponseDto;

// Start the app but using a random port to avoid conflicts
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")

public class ParkingLotApplicationIntegrationTests {

    private class TestFixture {
		public static final int INVALID_ID = Integer.MAX_VALUE;

		public static final double MONTHLY_FEE = 140.5;

        public static final double HOURLY_FEE = 12.5;

        public static final Time OPEN_TIME = Time.valueOf("05:00:00");

		public static final Time CLOSE_TIME = Time.valueOf("09:00:00");

		private int id;

		public int getApplicationID() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}


    private TestFixture fixture;
	@Autowired
	private ParkingLotApplicationRepository parkingLotApplicationRepository;
	@Autowired
	private TestRestTemplate client;

	@BeforeAll
	public void setupTestFixture() {
		this.fixture = new TestFixture();
	}

	@BeforeAll
	@AfterAll
	public void clearDatabase() {
		parkingLotApplicationRepository.deleteAll();
	}


    @Test
	@Order(0)
	public void testCreateParkingLotApplication() {
		final double hourlyFee = TestFixture.HOURLY_FEE;
        final double monthlyFee = TestFixture.MONTHLY_FEE;
        final Time openTime = TestFixture.OPEN_TIME;
        final Time closeTime = TestFixture.CLOSE_TIME;

		ParkingLotApplicationRequestDto request = new ParkingLotApplicationRequestDto();
        request.setCloseTime(closeTime);
		request.setOpenTime(openTime);
		request.setHourlyFee(hourlyFee);
        request.setMonthlyFee(monthlyFee);

		ResponseEntity<ParkingLotApplicationResponseDto> response = client.postForEntity("/application", request, ParkingLotApplicationResponseDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(hourlyFee, response.getBody().getHourlyFee());
        assertEquals(monthlyFee, response.getBody().getMonthlyFee());
        assertEquals(openTime, response.getBody().getOpenTime());
        assertEquals(closeTime, response.getBody().getCloseTime());
		assertTrue(response.getBody().getApplicationId() >= 1, "Response ID is at least 1.");

		// Save the ID so that later tests can use it
		fixture.setId(response.getBody().getApplicationId());
	}

    @Test
	@Order(1)
	public void testGetApplication() {
		int id = fixture.getApplicationID();

		ResponseEntity<ParkingLotApplicationResponseDto> response = client.getForEntity("/application/" + id, ParkingLotApplicationResponseDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
        assertEquals(TestFixture.HOURLY_FEE, response.getBody().getHourlyFee());
		assertEquals(TestFixture.MONTHLY_FEE, response.getBody().getMonthlyFee());
		assertEquals(TestFixture.OPEN_TIME, response.getBody().getOpenTime());
		assertEquals(TestFixture.CLOSE_TIME, response.getBody().getCloseTime());
		assertEquals(id, response.getBody().getApplicationId());
	}

	@Test
	@Order(2)
	public void testCreateInvalidApplication() {
		ParkingLotApplicationRequestDto request = new ParkingLotApplicationRequestDto();

		ResponseEntity<String> response = client.postForEntity("/application", request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(3)
	public void testGetInvalidApplication() {
		ResponseEntity<String> response = client.getForEntity("/application/" + TestFixture.INVALID_ID, String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Not found.", response.getBody());
	}

	@Test
	@Order(4)
	public void testUpdateAttributesParkingLotApplication() {

		int id = fixture.getApplicationID();

		final double UpdatedhourlyFee = 255.9;
        final double monthlyFee = TestFixture.MONTHLY_FEE;
        final Time openTime = TestFixture.OPEN_TIME;
        final Time closeTime = TestFixture.CLOSE_TIME;

		ParkingLotApplicationRequestDto request = new ParkingLotApplicationRequestDto();
        request.setCloseTime(closeTime);
		request.setOpenTime(openTime);
		request.setHourlyFee(UpdatedhourlyFee);
        request.setMonthlyFee(monthlyFee);

		//update in data base 
		client.put("/application/" + id, request);

		//get updated object
		ResponseEntity<ParkingLotApplicationResponseDto> response = client.getForEntity("/application/" + id, ParkingLotApplicationResponseDto.class);

        //assertNotNull(application);
		assertEquals(HttpStatus.OK, response.getStatusCode());
       	assertNotNull(response.getBody());
        assertEquals(UpdatedhourlyFee, response.getBody().getHourlyFee());
		assertEquals(TestFixture.MONTHLY_FEE, response.getBody().getMonthlyFee());
		assertEquals(TestFixture.OPEN_TIME, response.getBody().getOpenTime());
		assertEquals(TestFixture.CLOSE_TIME, response.getBody().getCloseTime());
		assertEquals(id, response.getBody().getApplicationId());;
	}

}
