package ca.mcgill.ecse321.parkinglotapplication.dao;

import java.util.List;
import java.sql.Date;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.Services;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;
//import ca.mcgill.ecse321.parkinglotapplication.model.Services;
//import ca.mcgill.ecse321.parkinglotapplication.model.Bill;


public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, Integer>{

    //PRIMARY KEY
    ServiceRequest findServiceRequestByServiceRequestID(int serviceID);

    //delete all
    void deleteAll();

    //find all service requests
    List<ServiceRequest> findAll();

    //find by status
    List<ServiceRequest> findByStatus(Status staus);

    //find all SR before date 
    List<ServiceRequest> findByDateBefore(Date date);

    //find all SR after date
    List<ServiceRequest> findByDateAfter(Date date);


    //associated table queries

    //Find service request for a service
    ServiceRequest findServiceRequestByServices(Services service);

    //Find service requests for a bill
    // List<ServiceRequest> findByBill(Bill bill); 
    
}
