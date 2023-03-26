package ca.mcgill.ecse321.parkinglotapplication.dto;

import java.sql.Time;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;


public class ParkingLotApplicationResponseDto {

    private int applicationId;
    private double hourlyFee;
    private double monthlyFee;
    private Time openTime;
    private Time closeTime;

    ParkingLotApplicationResponseDto() {}

    public ParkingLotApplicationResponseDto(ParkingLotApplication application){
        
        this.hourlyFee = application.getHourlyFee();
        this.monthlyFee = application.getMonthlyFee();
        this.openTime = application.getOpenTime();
        this.closeTime = application.getCloseTime();
        this.applicationId = application.getApplicationID();
    }

    public int getApplicationId() {
        return applicationId;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public double getHourlyFee() {
        return hourlyFee;
    }
    
    public double getMonthlyFee() {
        return monthlyFee;
    }
}
