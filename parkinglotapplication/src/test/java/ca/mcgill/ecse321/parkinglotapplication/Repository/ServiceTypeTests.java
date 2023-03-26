package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.ServiceTypeRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType.ServiceJob;

@SpringBootTest
public class ServiceTypeTests {
	
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		serviceTypeRepository.deleteAll();
	}
	
	@Test
	public void testServiceType() {
		//Create object
		ServiceType serviceType = new ServiceType();
        
        Double price = 13.0;
        String description = "Test";
        ServiceJob service = ServiceJob.OilChange;

		serviceType.setPrice(price);
		serviceType.setDescription(description);
        serviceType.setServiceJob(service);


		//Save Object
		serviceType = serviceTypeRepository.save(serviceType);

		//Read object from database
		serviceType = serviceTypeRepository.findServicetypeByServiceID(serviceType.getServiceID());
		
		//Assert that object has correct attributes
		assertNotNull(serviceType);
		assertEquals(price, serviceType.getPrice());
		assertEquals(description, serviceType.getDescription());
		assertEquals(service, serviceType.getServiceJob());
	}
	
	@Test
	void testDeleteServiceType(){
        //Create object
		ServiceType serviceType = new ServiceType();
        
        Double price = 13.0;
        String description = "Test";
        ServiceJob service = ServiceJob.OilChange;

		serviceType.setPrice(price);
		serviceType.setDescription(description);
        serviceType.setServiceJob(service);


		//Save Object
		serviceType = serviceTypeRepository.save(serviceType);

		//Read object from database
		serviceType = serviceTypeRepository.findServicetypeByServiceID(serviceType.getServiceID());
		assertNotNull(serviceType);

		//Delete object from database
		serviceTypeRepository.delete(serviceType);

		//Assert that database doesn't have object
		assertNull(serviceTypeRepository.findServicetypeByServiceID(serviceType.getServiceID()));

	}
}
