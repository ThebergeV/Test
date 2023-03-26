package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.MonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;


@SpringBootTest
public class MonthlyUserTests {
	
	@Autowired
	private MonthlyUserRepository monthlyUserRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		monthlyUserRepository.deleteAll();
	}
	
	@Test
	public void testMonthlyUser() {
		//Create object
		Floor floor = Floor.ONE;
		ParkingSpot parkingSpot = new ParkingSpot(floor, false);
		MonthlyUser monthlyUser = new MonthlyUser();

		String name = "Speed Racer";
		String email = "speedRacer@email.com";
		String number = "6";
		String password = "moo";
        
		monthlyUser.setName(name);
		monthlyUser.setEmail(email);
		monthlyUser.setNumber(number);
        monthlyUser.setParkingSpot(parkingSpot);
		monthlyUser.setPassword(password);

		//Save Object
		monthlyUser = monthlyUserRepository.save(monthlyUser);

		//Read object from database
		monthlyUser = monthlyUserRepository.findMonthlyUserByAccountId(monthlyUser.getAccountId());
		//Assert that object has correct attributes
		assertNotNull(monthlyUser);
		assertEquals(name, monthlyUser.getName());
		assertEquals(email, monthlyUser.getEmail());
		assertEquals(number, monthlyUser.getNumber());
		assertEquals(password, monthlyUser.getPassword());
        assertEquals(parkingSpot.getParkingID(), monthlyUser.getParkingSpot().getParkingID());
	}
	
	@Test
	void testDeleteMonthlyUser(){
        		//Create object
		Floor floor = Floor.ONE;
		ParkingSpot parkingSpot = new ParkingSpot(floor, false);
		MonthlyUser monthlyUser = new MonthlyUser();

		String name = "Speed Racer";
		String email = "speedRacer@email.com";
		String number = "6";
        String password = "moo";
        
		monthlyUser.setName(name);
		monthlyUser.setEmail(email);
		monthlyUser.setNumber(number);
        monthlyUser.setParkingSpot(parkingSpot);
		monthlyUser.setPassword(password);

		//Save Object
		monthlyUser = monthlyUserRepository.save(monthlyUser);

		//Read object from database
		monthlyUser = monthlyUserRepository.findMonthlyUserByAccountId(monthlyUser.getAccountId());
		assertNotNull(monthlyUser);

		//Delete object from database
		monthlyUserRepository.delete(monthlyUser);;

		//Assert that database doesn't have object
		assertNull(monthlyUserRepository.findMonthlyUserByAccountId(monthlyUser.getAccountId()));
	}
}