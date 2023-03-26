package ca.mcgill.ecse321.parkinglotapplication.dto;

import java.sql.Date;

import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.Services;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;

public class ServiceRequestResponseDto {
    //fields
    private Date date;
    private Status status;
    private Services services;
    private Bill bill;
    private int serviceRequestID;

    public ServiceRequestResponseDto() {}

    public ServiceRequestResponseDto(ServiceRequest serviceRequest) {
        this.date = serviceRequest.getDate();
        this.status = serviceRequest.getStatus();
        this.services = serviceRequest.getServices();
        this.bill = serviceRequest.getBill();
    }

    //GETTERS

    public Date getDate() {
        return this.date;
    }

    public Status getStatus() {
        return this.status;
    }

    public Services getServices() {
        return this.services;
    }

    public Bill getBill() {
        return this.bill;
    }

    public int getServiceRequestID() {
        return serviceRequestID;
    }
}
