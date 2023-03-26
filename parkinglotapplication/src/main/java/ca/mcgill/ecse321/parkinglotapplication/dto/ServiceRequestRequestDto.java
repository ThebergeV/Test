package ca.mcgill.ecse321.parkinglotapplication.dto;

import java.sql.Date;

import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest;
import ca.mcgill.ecse321.parkinglotapplication.model.Services;
import ca.mcgill.ecse321.parkinglotapplication.model.ServiceRequest.Status;

public class ServiceRequestRequestDto {
    //fields
    private Date date;
    private Status status;
    private Services services;
    private Bill bill;
    private int serviceRequestID;

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
        return this.serviceRequestID;
    }

    //SETTERS

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setServiceRequestID(int id) {
        this.serviceRequestID = serviceRequestID;
    }

    //wrap method

    public ServiceRequest toModel() {
        ServiceRequest serviceRequest = new ServiceRequest(date, status, services, bill);
        return serviceRequest;
    }
}
