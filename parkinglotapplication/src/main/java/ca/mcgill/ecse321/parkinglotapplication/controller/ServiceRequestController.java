package ca.mcgill.ecse321.parkinglotapplication.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotapplication.dto.ServiceRequestRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.ServiceRequestResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.Services;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;
import ca.mcgill.ecse321.parkinglotapplication.service.ServiceRequestService;

@RestController
public class ServiceRequestController {
    //intit service object for service method access
    @Autowired
    private ServiceRequestService serviceRequestService;

    /**
     * gets resp entity got fetting service req by id
     * @param serviceRequestID
     * @return response entity fot getting service req
     */

    @GetMapping("/servicerequest/{serviceRequestID}")
    public ResponseEntity<ServiceRequestResponseDto> getServiceRequestByServiceRequestID(@PathVariable int serviceRequestID) {
        ServiceRequest serviceRequest = serviceRequestService.getServiceRequestByServiceRequestID(serviceRequestID);
        ServiceRequestResponseDto responseObject = new ServiceRequestResponseDto(serviceRequest);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    /**
     *  resp entity for updating
     * 
     * @param serviceRequestID
     * @param serviceRequestRequestDto
     * @return resp entity for updating
     */
    @PutMapping("/servicerequest/{serviceRequestID}")
    public ResponseEntity<ServiceRequestResponseDto> updateServiceRequest(@PathVariable int serviceRequestID, @RequestBody ServiceRequestRequestDto serviceRequestRequestDto) {
        //get updated fields
        Bill bill = serviceRequestRequestDto.getBill();
        Services services = serviceRequestRequestDto.getServices();
        Date date = serviceRequestRequestDto.getDate();
        Status status = serviceRequestRequestDto.getStatus();
        //model
        ServiceRequest serviceRequest = serviceRequestRequestDto.toModel();
        //reinit service req fields to fix model nullify and make updates
        serviceRequest.setBill(bill);
        serviceRequest.setServices(services);
        serviceRequest.setDate(date);
        serviceRequest.setStatus(status);
        serviceRequest.setServiceRequestID(serviceRequestID);

        serviceRequest = serviceRequestService.updateServiceRequest(serviceRequestID, serviceRequest);

        ServiceRequestResponseDto responseObject = new ServiceRequestResponseDto(serviceRequest);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    /**
     * resp entity for creation
     * 
     * @param serviceRequestDto
     * @param accountHolder
     * @param customerType
     * @return resp entity for creation
     */
    @PostMapping("/servicerequest")
    public ResponseEntity<ServiceRequestResponseDto> createServiceRequest(@RequestBody ServiceRequestRequestDto serviceRequestDto, AccountHolder accountHolder, CustomerType customerType) {
        ServiceRequest serviceRequest = serviceRequestDto.toModel();
        serviceRequest = serviceRequestService.createServiceRequest(serviceRequest, accountHolder, customerType);
        ServiceRequestResponseDto responseObject = new ServiceRequestResponseDto(serviceRequest);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }
}
