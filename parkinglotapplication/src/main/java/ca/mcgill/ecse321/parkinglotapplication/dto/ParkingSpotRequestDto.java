package ca.mcgill.ecse321.parkinglotapplication.dto;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

public class ParkingSpotRequestDto {
    
    private Floor floor;
    private int parkingID;
    private boolean isAvailable;

    public void setFloor(Floor aFloor){
        floor = aFloor;
    }
  
    public void setIsAvailable(boolean aIsAvailable){
        isAvailable = aIsAvailable;
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

    public ParkingSpot toModel() {

		ParkingSpot aParkingSpot = new ParkingSpot(floor,isAvailable);
        
		return aParkingSpot;
	}
}
