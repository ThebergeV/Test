package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.BillRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ServiceTypeRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType.ServiceJob;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;

@SpringBootTest
public class ServiceRequestTests {
	
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;
	
	@Autowired
	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private NonMonthlyUserRepository nonMonthlyUserRepository;
	
	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		nonMonthlyUserRepository.deleteAll();
		serviceTypeRepository.deleteAll();
		serviceRequestRepository.deleteAll();
		billRepository.deleteAll();
	}
	
	@Test
	public void testServiceRequest() {

		//Create object
		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser();
		String name = "Racer X";
		String email = "definetly.not.rexracer@email.com";
		String number = "9";
		String licensePlateNumber = "RAC3RX";
        
		nonMonthlyUser.setName(name);
		nonMonthlyUser.setEmail(email);
		nonMonthlyUser.setNumber(number);
        nonMonthlyUser.setLicensePlateNumber(licensePlateNumber);
		
		Bill bill = new Bill();
		int price = 44;
		Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
		boolean isPaid = true;
		CustomerType customerType = CustomerType.AccountHolder;

		bill.setAccountHolder(nonMonthlyUser);
		bill.setPrice(price);
		bill.setDate(date);
		bill.setIsPaid(isPaid);
		bill.setCustomerType(customerType);

		//Save Object
		nonMonthlyUser = nonMonthlyUserRepository.save(nonMonthlyUser);
		bill = billRepository.save(bill);

		//make a service type
        ServiceType service = new ServiceType(44, "description",ServiceJob.OilChange);

		//make a service request
        ServiceRequest serviceRequest = new ServiceRequest(date, Status.Completed, service, bill);

		//Save Object
		service = serviceTypeRepository.save(service);
		serviceRequest = serviceRequestRepository.save(serviceRequest);

		//Read object from database
		serviceRequest = serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID());
		
		//Assert that object has correct attributes
		assertNotNull(serviceRequest);
		assertEquals(date, serviceRequest.getDate());
		assertEquals(Status.Completed, serviceRequest.getStatus());
		assertEquals(bill.getTransactionID(), serviceRequest.getBill().getTransactionID());
	}
	
	@Test
	void testDeleteServiceRequest(){
		
		//Create object
		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser();
		String name = "Racer X";
		String email = "definetly.not.rexracer@email.com";
		String number = "9";
		String licensePlateNumber = "RAC3RX";
        
		nonMonthlyUser.setName(name);
		nonMonthlyUser.setEmail(email);
		nonMonthlyUser.setNumber(number);
        nonMonthlyUser.setLicensePlateNumber(licensePlateNumber);
		
		Bill bill = new Bill();
		int price = 44;
		Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
		boolean isPaid = true;
		CustomerType customerType = CustomerType.AccountHolder;

		bill.setAccountHolder(nonMonthlyUser);
		bill.setPrice(price);
		bill.setDate(date);
		bill.setIsPaid(isPaid);
		bill.setCustomerType(customerType);

		//Save Object
		nonMonthlyUser = nonMonthlyUserRepository.save(nonMonthlyUser);
		bill = billRepository.save(bill);
		
		//make a service type
        ServiceType service = new ServiceType(44, "description",ServiceJob.OilChange);

		//make a service request
        ServiceRequest serviceRequest = new ServiceRequest(date, Status.Completed, service, bill);

		
		//Save Object
		service = serviceTypeRepository.save(service);
		serviceRequest = serviceRequestRepository.save(serviceRequest);

		//Read object from database
		serviceRequest = serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID());

		//Delete object from database
		serviceRequestRepository.delete(serviceRequest);

		//Assert that database doesn't have object
        assertNull(serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID()));
    
	}
}
