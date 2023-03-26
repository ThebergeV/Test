package ca.mcgill.ecse321.parkinglotapplication.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.parkinglotapplication.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotapplication.dto.ServiceRequestRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.ServiceRequestResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType.ServiceJob;

//avoid port conflict
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//explict test class lifecycle
@TestInstance(Lifecycle.PER_CLASS)
//explicit test order
@TestMethodOrder(OrderAnnotation.class)
//@SuppressWarnings("null")

public class ServiceRequestIntegrationTests {

    //create persistent test situation
    private class TestFixture {

        //comparison value for id not able to be contained in Integer type
        public static final int INVALID_ID = Integer.MAX_VALUE;

        public static final long millis = System.currentTimeMillis();
        public static final Date date = new Date(millis);
        public static final Status status = ServiceRequest.Status.Completed;
        
        public static final ServiceType services = new ServiceType(19.99, "tire change", ServiceJob.TireChange);
        public static final Bill bill = new Bill();

        private int id;

        public int getServiceRequestID() {
            return id;
        }

        public void setServiceRequestID(int id) {
            this.id = id;
        }

    }

    private TestFixture fixture;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private TestRestTemplate restful;

    @BeforeAll
    public void setupTestFixture() {
        this.fixture = new TestFixture();
    }

    @BeforeAll
    @AfterAll
    public void clear() {
        serviceRequestRepository.deleteAll();
    }

    @Test
    public void testCreateServiceRequest() {
        //get attributes for test
        final Date date = TestFixture.date;
        final Status status = TestFixture.status;
        final ServiceType services = TestFixture.services;
        final Bill bill = TestFixture.bill;


        //format data transfer obj
        ServiceRequestRequestDto req = new ServiceRequestRequestDto();
        req.setDate(date);
        req.setStatus(status);
        req.setServices(services);
        req.setBill(bill);

        ResponseEntity<ServiceRequestResponseDto> resp = restful.postForEntity("/servicerequest", req, ServiceRequestResponseDto.class);

        //assertions
        //attributes
        assertNotNull(resp.getBody()); //check that return not null
        assertEquals(date, resp.getBody().getDate());
        assertEquals(status, resp.getBody().getStatus());
        assertEquals(services, resp.getBody().getServices());
        assertEquals(bill, resp.getBody().getBill());
        //verification of process
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());

        fixture.setServiceRequestID(resp.getBody().getServiceRequestID());

    }

    @Test
    public void testGetServiceRequest() {
        int serviceRequestID = fixture.getServiceRequestID();
        //get attributes for test
        final Date date = TestFixture.date;
        final Status status = TestFixture.status;
        final ServiceType services = TestFixture.services;
        final Bill bill = TestFixture.bill;

        ResponseEntity<ServiceRequestResponseDto> resp = restful.getForEntity("/servicerequest/" + serviceRequestID, ServiceRequestResponseDto.class);

        //assertions
        //attributes
        assertNotNull(resp.getBody()); //check that return not null
        assertEquals(date, resp.getBody().getDate());
        assertEquals(status, resp.getBody().getStatus());
        assertEquals(services, resp.getBody().getServices());
        assertEquals(bill, resp.getBody().getBill());
        //verification of process
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());

    }

    @Test
    public void testCreateServiceRequestInvalid() {
        ServiceRequestRequestDto req = new ServiceRequestRequestDto();

        ResponseEntity<String> resp = restful.postForEntity("/servicerequest", req, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    public void testGetServiceRequestInvalid() {
        ResponseEntity<String> resp = restful.getForEntity("/servicerequest/" + TestFixture.INVALID_ID, String.class);

        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals("Service Request not found", resp.getBody());
    }

    @Test
    public void testUpdateServiceRequest() {
        int serviceRequestID = fixture.getServiceRequestID();
        //get attributes for test
        final Date date = TestFixture.date;
        final Status status = TestFixture.status;
        final ServiceType services = TestFixture.services;
        final Bill bill = TestFixture.bill;

        //format data transfer obj
        ServiceRequestRequestDto req = new ServiceRequestRequestDto();
        req.setDate(date);
        req.setStatus(status);
        req.setServices(services);
        req.setBill(bill);

        //persist
        restful.put("/servicerequest" + serviceRequestID, req);

        //post method object
        ResponseEntity<ServiceRequestResponseDto> resp = restful.getForEntity("/servicerequest" + serviceRequestID, ServiceRequestResponseDto.class);

        //assertions
        //attributes
        assertNotNull(resp.getBody()); //check that return not null
        assertEquals(date, resp.getBody().getDate());
        assertEquals(status, resp.getBody().getStatus());
        assertEquals(services, resp.getBody().getServices());
        assertEquals(bill, resp.getBody().getBill());
        //verification of process
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
    }

}
