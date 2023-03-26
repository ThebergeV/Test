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

import ca.mcgill.ecse321.parkinglotapplication.dao.MonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.dto.MonthlyUserRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.MonthlyUserResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

// Start the app but using a random port to avoid conflicts
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order 
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
public class MonthlyUserIntegrationTests {


        // Stores state to be shared between tests
        private class TestFixture {
            public static final int INVALID_ID = Integer.MAX_VALUE;
    
            public static final String USER_NAME = "Rick";
    
            public static final String USER_EMAIL = "Rick@mail.com";
    
            public static final String USER_NUMBER = "519-090-0099";
    
            public static final String USER_PASSWORD = "password123";

            public static final String USER_LICENSE = "12A BCD";
    
            private int id;

            private ParkingSpot parkingSpot;
    
            public int getAccountId() {
                return id;
            }

            public ParkingSpot getParkingSpot() {
                return parkingSpot;
            }
    
            public void setId(int id) {
                this.id = id;
            }

            public void setParkingSpot(ParkingSpot parkingSpot) {
                this.parkingSpot = parkingSpot;
            }
        }
    
        private TestFixture fixture;

        @Autowired
        private ParkingSpotRepository parkingSpotRepository;

        @Autowired
        private MonthlyUserRepository monthlyUserRepository;

        @Autowired
        private TestRestTemplate client;
    
        @BeforeAll
        public void setupTestFixture() {
            this.fixture = new TestFixture();
        }

    @BeforeAll
	@AfterAll //had to cleat monthly before parkingSpot because of association!! haha
	public void clearDatabase() {
    monthlyUserRepository.deleteAll();
    parkingSpotRepository.deleteAll();

	}

    
    ParkingSpot parkingSpot = new ParkingSpot(Floor.THREE, true);

    @Test
        @Order(0)
        public void testCreateMonthlyUser() {
            String name = "Rick";
            String password = "password123";
            String number = "519-090-0099";
            String email = "Rick@mail.com";
            String licensePlateNumber = "12A BCD";
    
            MonthlyUserRequestDto request = new MonthlyUserRequestDto();
            request.setEmail(email);
            request.setName(name);
            request.setPassword(password);
            request.setNumber(number);
            request.setLicensePlate(licensePlateNumber);
            request.setParkingSpot(parkingSpot);
    
            ResponseEntity<MonthlyUserResponseDto> response = client.postForEntity("/monthlyuser", request, MonthlyUserResponseDto.class);
    
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(name, response.getBody().getName());
            assertEquals(password, response.getBody().getPassword());
            assertEquals(email, response.getBody().getEmail());
            assertEquals(number, response.getBody().getNumber());
            assertEquals(licensePlateNumber, response.getBody().getLicensePlateNumber());
            assertTrue(response.getBody().getAccountId() >= 1, "Response ID is at least 1.");
    
            // Save the ID so that later tests can use it
            fixture.setId(response.getBody().getAccountId());

            fixture.setParkingSpot(response.getBody().getParkingSpot());
        }
    
        @Test 
        @Order(2)
        public void testGetMonthlyUser() {
                int id = fixture.getAccountId();
                ParkingSpot parkingSpot = fixture.getParkingSpot();
        
                ResponseEntity<MonthlyUserResponseDto> response = client.getForEntity("/monthlyuser/" + id, MonthlyUserResponseDto.class);
        
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(TestFixture.USER_EMAIL, response.getBody().getEmail());
                assertEquals(TestFixture.USER_NAME, response.getBody().getName());
                assertEquals(TestFixture.USER_NUMBER, response.getBody().getNumber());
                assertEquals(TestFixture.USER_PASSWORD, response.getBody().getPassword());
                assertEquals(TestFixture.USER_LICENSE, response.getBody().getLicensePlateNumber());
                assertEquals(parkingSpot.getParkingID(), response.getBody().getParkingSpot().getParkingID());
                assertEquals(id, response.getBody().getAccountId());
    
        }

        @Test
        @Order(3)
        public void testCreateInvalidMonthlyUser() {
            MonthlyUserRequestDto request = new MonthlyUserRequestDto();
    
            ResponseEntity<String> response = client.postForEntity("/monthlyuser", request, String.class);
    
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        }
    
        @Test
        @Order(4)
        public void testGetInvalidMonthlyUser() {
            ResponseEntity<String> response = client.getForEntity("/monthlyuser/" + TestFixture.INVALID_ID, String.class);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Monthly User not found", response.getBody());
        }

        @Test
	    @Order(5)
	    public void testUpdateLicensePlateMonthlyUser() {
        int accountId = fixture.getAccountId();
        ParkingSpot parkingSpot = new ParkingSpot(Floor.THREE, true);

        parkingSpotRepository.save(parkingSpot);

		String name = "Rick";
        String password = "password123";
        String number = "519-090-0099";
        String email = "Rick@mail.com";
        String newlicensePlateNumber = "12A AAA";
    
        MonthlyUserRequestDto request = new MonthlyUserRequestDto();
        request.setEmail(email);
        request.setName(name);
        request.setPassword(password);
        request.setNumber(number);
        request.setLicensePlate(newlicensePlateNumber);
        request.setParkingSpot(parkingSpot);
    
		//update in data base 
		client.put("/monthlyuser/" + accountId, request);

		//get updated object
		ResponseEntity<MonthlyUserResponseDto> response = client.getForEntity("/monthlyuser/" + accountId, MonthlyUserResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TestFixture.USER_EMAIL, response.getBody().getEmail());
        assertEquals(TestFixture.USER_NAME, response.getBody().getName());
        assertEquals(TestFixture.USER_NUMBER, response.getBody().getNumber());
        assertEquals(TestFixture.USER_PASSWORD, response.getBody().getPassword());
        assertEquals(newlicensePlateNumber, response.getBody().getLicensePlateNumber());
        assertEquals(parkingSpot.getParkingID(), response.getBody().getParkingSpot().getParkingID());
        assertEquals(accountId, response.getBody().getAccountId());
    
    }
}
