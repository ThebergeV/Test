package ca.mcgill.ecse321.parkinglotapplication.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.BillRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.MonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType;
import ca.mcgill.ecse321.parkinglotapplication.model.Services;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType.ServiceJob;
import ca.mcgill.ecse321.parkinglotapplication.service.ServiceRequestService;

@SpringBootTest
public class ServiceRequestServiceTests {

    //working test repo
    @Mock
    private ServiceRequestRepository serviceRequestRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MonthlyUserRepository monthlyUserRepository;

    @Mock
    private NonMonthlyUserRepository nonMonthlyUserRepository;
    
    @Mock
    private BillRepository billRepository;

    //access to service req methods to test
    @InjectMocks
    private ServiceRequestService serviceRequestService;

    //begin tests
    @Test
    public void testCreateServiceRequest() {
        //init test object attributes
        //service request attibutes
        long millis = System.currentTimeMillis();
        Date date = new Date(millis); 
        //services creation
        double price = 19.99;
        String description = "Car Wash";
        ServiceJob serviceJob = ServiceJob.Wash;
        Services services = new ServiceType(price, description, serviceJob);
        //bill creation
        CustomerType customer = CustomerType.AccountHolder;
        boolean isPaid = false;
        //account holder creation
        String name = "Peter Quinley";
        String email = "peter@quinley.ru";
        String number = "902-310-3030";
        String pass = "password";
        String license = "PLATE";
        //parking spot creation
        boolean isAvailable = false;
        ParkingSpot parkingSpot = new ParkingSpot(ParkingSpot.Floor.FIVE, isAvailable);
        MonthlyUser user = new MonthlyUser(name, email, number, pass, license, parkingSpot);
        monthlyUserRepository.save(user);
        Bill bill = new Bill(price, date, customer, isPaid, user);
        billRepository.save(bill);
        
        
        //test object
        final ServiceRequest serviceRequest = new ServiceRequest(date, ServiceRequest.Status.InProgress, services, bill);
        
        when(serviceRequestRepository.save(serviceRequest)).thenReturn(serviceRequest);

        ServiceRequest testRequest = serviceRequestService.createServiceRequest(serviceRequest, user, customer);
        
        //checks
        assertNotNull(testRequest);
        assertEquals(date, testRequest.getDate());
        assertEquals(ServiceRequest.Status.InProgress, testRequest.getStatus());
        assertEquals(services, testRequest.getServices());
    }

    @Test
    public void testCreateServiceRequestInvalid() {
        //init test object empty

        final ServiceRequest serviceRequest = new ServiceRequest();

        ParkingLotApplicationException exception = assertThrows(ParkingLotApplicationException.class, () -> serviceRequestService.createServiceRequest(serviceRequest, null, null));

        assertEquals("One or many fields empty", exception.getMessage());
   }

    @Test
    public void testGetServiceRequestByServiceRequestID() {
        //init test object attributes
        //service request attibutes
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        //services creation
        double price = 246.67;
        String description = "Tires";
        ServiceJob serviceJob = ServiceJob.TireChange;
        Services services = new ServiceType(price, description, serviceJob);
        //bill creation
        CustomerType customer = CustomerType.AccountHolder;
        boolean isPaid = false;
        //account holder creation
        String name = "Winston Churchill";
        String email = "ministerprime@yahoo.co.uk";
        String number = "440-150-7668";
        String pass = "password123";
        String license = "MOBILE";
        //parking spot creation
        boolean isAvailable = false;
        ParkingSpot parkingSpot = new ParkingSpot(ParkingSpot.Floor.ONE, isAvailable);
        MonthlyUser user = new MonthlyUser(name, email, number, pass, license, parkingSpot);
        Bill bill = new Bill(price, date, customer, isPaid, user);
        
        //test object
        final ServiceRequest serviceRequest = new ServiceRequest(date, ServiceRequest.Status.InProgress, services, bill);

        when(serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID())).thenReturn(serviceRequest);

        ServiceRequest testRequest = serviceRequestService.getServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID());

        //checks
        assertNotNull(testRequest);
        assertEquals(date, testRequest.getDate());
        assertEquals(ServiceRequest.Status.InProgress, testRequest.getStatus());
        assertEquals(services, testRequest.getServices());
        assertEquals(bill, testRequest.getBill());
    }

    @Test
    public void testGetServiceRequestByServiceRequestIDInvalid() {
        //init test object fields
        //final long millis = System.currentTimeMillis();
        //final Date date = new Date(millis);
        Status status = ServiceRequest.Status.InProgress;
        ServiceType services = new ServiceType();
        Bill bill = new Bill();

        //test object
        ServiceRequest serviceRequest = new ServiceRequest(null, status, services, bill);
        //note two null fields should result in "Service Request not found" exception

        ParkingLotApplicationException exception = assertThrows(ParkingLotApplicationException.class, () -> serviceRequestService.getServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID()));

        assertEquals("Service Request not found", exception.getMessage());
    }

    @Test
    public void testFinishServiceRequest() {
        //init test object attributes
        //service request attibutes
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        //services creation
        double price = 19.99;
        String description = "Car Wash";
        ServiceJob serviceJob = ServiceJob.Wash;
        Services services = new ServiceType(price, description, serviceJob);
        //bill creation
        CustomerType customer = CustomerType.AccountHolder;
        boolean isPaid = false;
        //account holder creation
        String name = "Peter Quinley";
        String email = "peter@quinley.ru";
        String number = "902-310-3030";
        String pass = "password";
        String license = "PLATE";
        //parking spot creation
        boolean isAvailable = false;
        ParkingSpot parkingSpot = new ParkingSpot(ParkingSpot.Floor.FIVE, isAvailable);
        MonthlyUser user = new MonthlyUser(name, email, number, pass, license, parkingSpot);
        Bill bill = new Bill(price, date, customer, isPaid, user);
        
        //test object
        final ServiceRequest serviceRequest = new ServiceRequest(date, ServiceRequest.Status.InProgress, services, bill);
        
        when(serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequest.getServiceRequestID())).thenReturn(serviceRequest);

        ServiceRequest testRequest = serviceRequestService.finishServiceRequest(serviceRequest.getServiceRequestID());

        //checks
        assertNotNull(testRequest);
        assertEquals(date, testRequest.getDate());
        assertEquals(ServiceRequest.Status.Completed, testRequest.getStatus());
        assertEquals(services, testRequest.getServices());
        assertEquals(bill, testRequest.getBill());

    }

    @Test
    public void testFinishServiceRequestInvalid() {
        //init nonexistant id
        int invalidID = 7;
        ParkingLotApplicationException exception = assertThrows(ParkingLotApplicationException.class, () -> serviceRequestService.finishServiceRequest(invalidID));

        assertEquals("Service Request not found", exception.getMessage());

    }

}
