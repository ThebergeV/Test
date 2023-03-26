package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingLotApplicationRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;

@SpringBootTest
public class ParkingLotApplicationTests {
	
	@Autowired
	private ParkingLotApplicationRepository parkingLotApplicationRepository;
	

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		parkingLotApplicationRepository.deleteAll();
	}
	
	@Test
	public void testParkingLotApplication() {
		//Create object
		Time openTime = new Time(4);
        Time closeTime = new Time(100);
        Double monthlyFee = 13.0;
        Double hourlyFee = 3.0;

		ParkingLotApplication parkingLotApplication = new ParkingLotApplication(openTime, closeTime, monthlyFee, hourlyFee, 13);

		//Save Object
		parkingLotApplication = parkingLotApplicationRepository.save(parkingLotApplication);

		//Read object from database
		parkingLotApplication = parkingLotApplicationRepository.findAppByApplicationID(parkingLotApplication.getApplicationID());
		
		//Assert that object has correct attributes
		assertNotNull(parkingLotApplication);
		assertEquals(openTime.toString(), parkingLotApplication.getOpenTime().toString());
		assertEquals(closeTime.toString(), parkingLotApplication.getCloseTime().toString());
		assertEquals(monthlyFee, parkingLotApplication.getMonthlyFee());
		assertEquals(hourlyFee, parkingLotApplication.getHourlyFee());
		assertTrue(parkingLotApplication.getApplicationID() > 0, "id should be greater than 0");
	}
	
	@Test
	void testDeleteParkingLotApplication(){
        //Create object
		Time openTime = new Time(4);
        Time closeTime = new Time(100);
        Double monthlyFee = 13.0;
        Double hourlyFee = 3.0;

		ParkingLotApplication parkingLotApplication = new ParkingLotApplication(openTime, closeTime, monthlyFee, hourlyFee, 13);


		//Save Object
		parkingLotApplication = parkingLotApplicationRepository.save(parkingLotApplication);

		//Read object from database
		parkingLotApplication = parkingLotApplicationRepository.findAppByApplicationID(parkingLotApplication.getApplicationID());
		assertNotNull(parkingLotApplication);

		//Delete object from database
		parkingLotApplicationRepository.delete(parkingLotApplication);

		//Assert that database doesn't have object
		assertNull(parkingLotApplicationRepository.findAppByApplicationID(parkingLotApplication.getApplicationID()));

	}
}
