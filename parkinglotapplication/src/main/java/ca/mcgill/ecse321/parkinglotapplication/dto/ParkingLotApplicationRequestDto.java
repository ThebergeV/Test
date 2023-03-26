package ca.mcgill.ecse321.parkinglotapplication.dto;

import java.sql.Time;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;

public class ParkingLotApplicationRequestDto {
    private double hourlyFee;
    private double monthlyFee;
    private Time openTime;
    private Time closeTime;
    private int applicationID;

    public double getMonthlyFee(){
        return monthlyFee;
    }

    public double getHourlyFee(){
        return hourlyFee;
    }

    public Time getCloseTime(){
        return closeTime;
    }

    public Time getOpenTime(){
        return openTime;
    }

    public int getApplicationID(){
        return applicationID;
    }

    public void setMonthlyFee(double monthlyFee){
        this.monthlyFee = monthlyFee;
    }

    public void setHourlyFee(double hourlyFee){
        this.hourlyFee = hourlyFee;
    }

    public void setOpenTime(Time openTime){
        this.openTime = openTime;
    }

    public void setCloseTime(Time closeTime){
        this.closeTime = closeTime;
    }

    public void setApplicationID(int id){
        this.applicationID = id;
    }

    public ParkingLotApplication toModel() {

		ParkingLotApplication application = new ParkingLotApplication(openTime, closeTime, monthlyFee, hourlyFee, applicationID);
        
		return application;
	}

    
}
