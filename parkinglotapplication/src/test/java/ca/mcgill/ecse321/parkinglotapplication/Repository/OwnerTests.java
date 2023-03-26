package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.OwnerRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.Owner;

@SpringBootTest
public class OwnerTests {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		ownerRepository.deleteAll();
	}
	
	@Test
	public void testOwner() {
		//Create object
		String name = "Pops Racer";
		String email = "popsRacer@email.com";
		String number = "4";
		String password = "moo";

        //Create object
		Owner owner = new Owner(name, email, number, password);

		//Save Object
		owner = ownerRepository.save(owner);

		//Read object from database
		owner = ownerRepository.findOwnerByAccountId(owner.getAccountId());
		
		//Assert that object has correct attributes
		assertNotNull(owner);
		assertEquals(name, owner.getName());
		assertEquals(email, owner.getEmail());
		assertEquals(number, owner.getNumber());
	}
	
	@Test
	void testDeleteOwner(){

		String name = "Pops Racer";
		String email = "popsRacer@email.com";
		String number = "4";
		String password = "moo";

        //Create object
		Owner owner = new Owner(name, email, number, password);
    
		//Save Object
		owner = ownerRepository.save(owner);

		//Read object from database
		owner = ownerRepository.findOwnerByAccountId(owner.getAccountId());

		assertNotNull(owner);

		//Delete object from database
		ownerRepository.delete(owner);

		//Assert that database doesn't have object
		assertNull(ownerRepository.findOwnerByAccountId(owner.getAccountId()));

	}
}
