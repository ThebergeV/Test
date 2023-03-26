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

import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dto.NonMonthlyUserRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.NonMonthlyUserResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

// Start the app but using a random port to avoid conflicts
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order 
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
public class NonMonthlyUserIntegrationTests {


        // Stores state to be shared between tests
        private class TestFixture {
            public static final int INVALID_ID = Integer.MAX_VALUE;
    
            public static final String USER_NAME = "Rick";
    
            public static final String USER_EMAIL = "Rick@mail.com";
    
            public static final String USER_NUMBER = "519-090-0099";
    
            public static final String USER_PASSWORD = "password123";

            public static final String USER_LICENSE = "12A BCD";
    
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
        private NonMonthlyUserRepository nonMonthlyUserRepository;

        @Autowired
        private TestRestTemplate client;
    
        @BeforeAll
        public void setupTestFixture() {
            this.fixture = new TestFixture();
        }

    @BeforeAll
	@AfterAll //had to cleat monthly before parkingSpot because of association!! haha
	public void clearDatabase() {
    nonMonthlyUserRepository.deleteAll();

	}

    
    ParkingSpot parkingSpot = new ParkingSpot(Floor.THREE, true);

    @Test
        @Order(0)
        public void testCreateNonMonthlyUser() {
            String name = "Rick";
            String password = "password123";
            String number = "519-090-0099";
            String email = "Rick@mail.com";
            String licensePlateNumber = "12A BCD";
    
            NonMonthlyUserRequestDto request = new NonMonthlyUserRequestDto();
            request.setEmail(email);
            request.setName(name);
            request.setPassword(password);
            request.setNumber(number);
            request.setLicensePlate(licensePlateNumber);
    
            ResponseEntity<NonMonthlyUserResponseDto> response = client.postForEntity("/nonmonthlyuser", request, NonMonthlyUserResponseDto.class);
    
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

        }
    
        @Test 
        @Order(2)
        public void testGetNonMonthlyUser() {
                int id = fixture.getAccountId();
        
                ResponseEntity<NonMonthlyUserResponseDto> response = client.getForEntity("/nonmonthlyuser/" + id, NonMonthlyUserResponseDto.class);
        
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(TestFixture.USER_EMAIL, response.getBody().getEmail());
                assertEquals(TestFixture.USER_NAME, response.getBody().getName());
                assertEquals(TestFixture.USER_NUMBER, response.getBody().getNumber());
                assertEquals(TestFixture.USER_PASSWORD, response.getBody().getPassword());
                assertEquals(TestFixture.USER_LICENSE, response.getBody().getLicensePlateNumber());
                assertEquals(id, response.getBody().getAccountId());
    
        }

        @Test
        @Order(3)
        public void testCreateInvalidNonMonthlyUser() {
            NonMonthlyUserRequestDto request = new NonMonthlyUserRequestDto();
    
            ResponseEntity<String> response = client.postForEntity("/nonmonthlyuser", request, String.class);
    
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    
        }
    
        @Test
        @Order(4)
        public void testGetInvalidNonMonthlyUser() {
            ResponseEntity<String> response = client.getForEntity("/nonmonthlyuser/" + TestFixture.INVALID_ID, String.class);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Non-Monthly User not found", response.getBody());
        }

        @Test
	    @Order(5)
	    public void testUpdateLicensePlateNonMonthlyUser() {
        int accountId = fixture.getAccountId();

		String name = "Rick";
        String password = "password123";
        String number = "519-090-0099";
        String email = "Rick@mail.com";
        String newlicensePlateNumber = "12A AAA";
    
        NonMonthlyUserRequestDto request = new NonMonthlyUserRequestDto();
        request.setEmail(email);
        request.setName(name);
        request.setPassword(password);
        request.setNumber(number);
        request.setLicensePlate(newlicensePlateNumber);
    
		//update in data base 
		client.put("/nonmonthlyuser/" + accountId, request);

		//get updated object
		ResponseEntity<NonMonthlyUserResponseDto> response = client.getForEntity("/nonmonthlyuser/" + accountId, NonMonthlyUserResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(accountId, response.getBody().getAccountId());
        assertEquals(TestFixture.USER_EMAIL, response.getBody().getEmail());
        assertEquals(TestFixture.USER_NAME, response.getBody().getName());
        assertEquals(TestFixture.USER_NUMBER, response.getBody().getNumber());
        assertEquals(TestFixture.USER_PASSWORD, response.getBody().getPassword());
        assertEquals(newlicensePlateNumber, response.getBody().getLicensePlateNumber());
        assertEquals(accountId, response.getBody().getAccountId());
    
    }
}
