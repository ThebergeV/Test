package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;


@SpringBootTest
public class NonMonthlyUserTests {
	
	@Autowired
	private NonMonthlyUserRepository nonNonMonthlyUserRepository;
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		nonNonMonthlyUserRepository.deleteAll();
	}
	
	@Test
	public void testNonMonthlyUser() {
		//Create object
		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser();

		String name = "Racer X";
		String email = "definetly.not.rexracer@email.com";
		String number = "9";
        String licensePlateNumber = "RAC3RX";
		String password = "moo";
        
		nonMonthlyUser.setName(name);
		nonMonthlyUser.setEmail(email);
		nonMonthlyUser.setNumber(number);
        nonMonthlyUser.setLicensePlateNumber(licensePlateNumber);
		nonMonthlyUser.setPassword(password);

		//Save Object
		nonMonthlyUser = nonNonMonthlyUserRepository.save(nonMonthlyUser);

		//Read object from database
		nonMonthlyUser = nonNonMonthlyUserRepository.findNonMonthlyUserByAccountId(nonMonthlyUser.getAccountId());
		//Assert that object has correct attributes
		assertNotNull(nonMonthlyUser);
		assertEquals(name, nonMonthlyUser.getName());
		assertEquals(email, nonMonthlyUser.getEmail());
		assertEquals(number, nonMonthlyUser.getNumber());
        assertEquals(licensePlateNumber, nonMonthlyUser.getLicensePlateNumber());

	}
	
	@Test
	void testDeleteNonMonthlyUser(){
        //Create object
		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser();

		String name = "Racer X";
		String email = "definetly.not.rexracer@email.com";
		String number = "9";
        String licensePlateNumber = "RAC3RX";
		String password = "moo";
        
		nonMonthlyUser.setName(name);
		nonMonthlyUser.setEmail(email);
		nonMonthlyUser.setNumber(number);
        nonMonthlyUser.setLicensePlateNumber(licensePlateNumber);
		nonMonthlyUser.setPassword(password);

		//Save Object
		nonMonthlyUser= nonNonMonthlyUserRepository.save(nonMonthlyUser);

		//Read object from database
		nonMonthlyUser = nonNonMonthlyUserRepository.findNonMonthlyUserByAccountId(nonMonthlyUser.getAccountId());
		
		assertNotNull(nonMonthlyUser);

		//Delete object from database
		nonNonMonthlyUserRepository.delete(nonMonthlyUser);;

		//Assert that database doesn't have object
		assertNull(nonNonMonthlyUserRepository.findNonMonthlyUserByAccountId(nonMonthlyUser.getAccountId()));


	}
}
