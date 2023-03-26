package ca.mcgill.ecse321.parkinglotapplication.dao;


import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

public interface ParkingSpotRepository extends CrudRepository<ParkingSpot, Integer>{

    //PRIMARY KEY
    ParkingSpot findParkingByParkingID(int parkingID);

    //delete by id
    ParkingSpot deleteByParkingID(int parkingID);

    //delete all Non Parking Spots
    void deleteAll();

    //find all Parking Spots
    List<ParkingSpot> findAll();

    //find all occupied or non occupied
    List<ParkingSpot> findByIsAvailable(Boolean isAvailable);

    //find all on floor
    List<ParkingSpot> findByFloor(Floor floor);

    
    
	
}
