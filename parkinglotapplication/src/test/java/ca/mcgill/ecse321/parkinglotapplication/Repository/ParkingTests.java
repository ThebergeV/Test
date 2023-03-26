package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.Parking;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

@SpringBootTest
public class ParkingTests {

	@Autowired
	private ParkingRepository parkingRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		parkingRepository.deleteAll();

	}
	
	@Test
	public void testParking() {

		//Create object
        ParkingSpot parkingSpot = new ParkingSpot();
		Floor floor = Floor.ONE;
		boolean isAvailable = false;

		parkingSpot.setFloor(floor);
		parkingSpot.setIsAvailable(isAvailable);

		Parking parking = new Parking();
        Double price = 13.0;
        String description = "Test";

		parking.setParkingSpot(parkingSpot);
		parking.setPrice(price);
		parking.setDescription(description);
 
		//Save Object

		parking = parkingRepository.save(parking);

		//Read object from database
		parking = parkingRepository.findParkingByServiceID(parking.getServiceID());
		
		//Assert that object has correct attributes
		assertNotNull(parking);
		assertEquals(price, parking.getPrice());
		assertEquals(description, parking.getDescription());
		assertEquals(parkingSpot.getParkingID(), parking.getParkingSpot().getParkingID());
	}
	
	@Test
	void testDeleteParking() {
       //Create object
		
        ParkingSpot parkingSpot = new ParkingSpot();
		Floor floor = Floor.ONE;
		boolean isAvailable = false;

		parkingSpot.setFloor(floor);
		parkingSpot.setIsAvailable(isAvailable);

		Parking parking = new Parking();
        Double price = 13.0;
        String description = "Test";
        
		parking.setParkingSpot(parkingSpot);
		parking.setPrice(price);
		parking.setDescription(description);

		//Save Object
		
		parking = parkingRepository.save(parking);

		//Delete object from database
		parkingRepository.delete(parking);

		//Assert that database doesn't have object
		assertNull(parkingRepository.findParkingByServiceID(parking.getServiceID()));

	}
}
