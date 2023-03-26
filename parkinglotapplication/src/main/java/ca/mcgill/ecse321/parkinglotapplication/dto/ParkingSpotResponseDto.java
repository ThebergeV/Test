package ca.mcgill.ecse321.parkinglotapplication.dto;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;


public class ParkingSpotResponseDto {

    private Floor floor;
    private int parkingID;
    private boolean isAvailable;

    ParkingSpotResponseDto() {}

    public ParkingSpotResponseDto(ParkingSpot aParkingSpot) {
        this.floor = aParkingSpot.getFloor();
        this.parkingID = aParkingSpot.getParkingID();
        this.isAvailable = aParkingSpot.getIsAvailable();
    }

    public Floor getFloor(){
        return floor;
    }

    public int getParkingID(){
        return parkingID;
    }

    public boolean getIsAvailable(){
        return isAvailable;
    }
}
