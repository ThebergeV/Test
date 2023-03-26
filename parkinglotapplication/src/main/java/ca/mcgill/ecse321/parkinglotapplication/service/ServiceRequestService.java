package ca.mcgill.ecse321.parkinglotapplication.service;


import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
//import ca.mcgill.ecse321.parkinglotapplication.dao.MonthlyUserRepository;
//import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ServiceRequestRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;
import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
//import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
//import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
//import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;
//import ca.mcgill.ecse321.parkinglotapplication.model.Services;

@Service
public class ServiceRequestService {

    //main repo obj
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    //External repos//
    //Account repos
    //@Autowired
    //private NonMonthlyUserRepository nonMonthlyUserRepository;
    //@Autowired
    //private MonthlyUserRepository monthlyUserRepository;
    //@Autowired
    //private EmployeeRepository employeeRepository;

    //create a new service request without adding to a bill
    /**
     * create service request service method
     * 
     * @param serviceRequest
     * @param account
     * @param customerType
     * @return ServiceRequest object after creation
     */
    @Transactional
    public ServiceRequest createServiceRequest(ServiceRequest serviceRequest, AccountHolder account, CustomerType customerType) {
        //check exitence of fields
        if (serviceRequest.getDate() == null || serviceRequest.getStatus() == null || serviceRequest.getServices() == null) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "One or many fields empty");
        }
        //get account type (intentional downcast)
        //AccountHolder account;
        //if (employeeRepository.findEmployeeByAccountId(accountID) != null) {
        //    account = (Employee) employeeRepository.findEmployeeByAccountId(accountID);
        //}
        //else if (nonMonthlyUserRepository.findNonMonthlyUserByAccountId(accountID) != null) {
        //    account = (NonMonthlyUser) nonMonthlyUserRepository.findNonMonthlyUserByAccountId(accountID);
        //}
        //else if (monthlyUserRepository.findMonthlyUserByAccountId(accountID) != null) {
        //    account = (MonthlyUser) monthlyUserRepository.findMonthlyUserByAccountId(accountID);
        //}
        //else {
        //    account = null;
        //}

        //check overall existence of account
        if (account == null) {
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Account to associate does not exist");
        }
        //create new bill
        double price = serviceRequest.getServices().getPrice();
        Date date = Date.valueOf(LocalDateTime.now().toLocalDate()); 
        boolean isPaid = false;
        
        Bill bill = new Bill(price, date, customerType, isPaid, account); //date instantiated as null
        //store bill
        //BillService billService = new BillService();
        //billService.createBill(bill); //persist bill

        //add bill to service request for eventual update
        serviceRequest.setBill(bill);

        //persist service request
        return serviceRequestRepository.save(serviceRequest);
    }

    /**
     * finish service request
     * change status to Complete
     * 
     * @param serviceRequestID
     * @return ServiceRequest after being finished (status set to complete)
     */
    //finish a job
    @Transactional
    public ServiceRequest finishServiceRequest(int serviceRequestID) {
        ServiceRequest serviceRequest = serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequestID);
        //check that instance exists
        if (serviceRequest == null) {
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Service Request not found");
        }
        //check if request is is already finished
        if (serviceRequest.getStatus() == Status.Completed) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Service request already complete");
        }
        //set status to complete
        serviceRequest.setStatus(Status.Completed);
        return serviceRequest;
    }

    /**
     * gets service request object by id
     * 
     * @param serviceRequestID
     * @return ServiceRequest object requested
     */
    //get service request by id
    @Transactional
    public ServiceRequest getServiceRequestByServiceRequestID(int serviceRequestID) {
        ServiceRequest serviceRequest = serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequestID);
        //check existence
        if (serviceRequest == null) {
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Service Request not found");
        }
        return serviceRequest;
    }

    /**
     * service request object returned after being updated based on parameter service request
     * 
     * @param serviceRequestID
     * @param serviceRequest
     * @return Service request object after update
     */
    //update service request object
    @Transactional
    public ServiceRequest updateServiceRequest(int serviceRequestID, ServiceRequest serviceRequest) {
        //check existence
        if (serviceRequestRepository.findServiceRequestByServiceRequestID(serviceRequestID) == null) {
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Service Request not found");
        }
        //check validity of update request
        if (serviceRequest.getDate() == null || serviceRequest.getStatus() == null || serviceRequest.getServices() == null) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "One or many fields empty");
        }
        return serviceRequestRepository.save(serviceRequest);
    }
}
