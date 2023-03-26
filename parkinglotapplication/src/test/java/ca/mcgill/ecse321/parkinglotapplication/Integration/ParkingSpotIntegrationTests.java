package ca.mcgill.ecse321.parkinglotapplication.Integration;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication.Floor;

// Start the app but using a random port to avoid conflicts
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// Reuse the same class for all the tests (instead of creating a new class each time).
@TestInstance(Lifecycle.PER_CLASS)
// Ensure the tests are run in the right order (e.g., POST before GET)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")

public class ParkingSpotIntegrationTests {

    private class TestFixture {
		public static final int INVALID_PARKING_ID = Integer.MAX_VALUE;

		public static final Floor FLOOR = Floor.ONE;

        public static final boolean IS_AVAILABLE = false;

		private int id;

		public int getParkingID() {
			return id;
		}

		public void setParkingtId(int id) {
			this.id = id;
		}
	}

    
}
