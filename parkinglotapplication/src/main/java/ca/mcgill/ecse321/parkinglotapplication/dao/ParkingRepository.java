package ca.mcgill.ecse321.parkinglotapplication.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.Parking;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;

public interface ParkingRepository extends CrudRepository<Parking, Integer>{

    //PRIMARY KEY
    Parking findParkingByServiceID(int serviceID);

    
    //associated table queries
    
    List<Parking> findByParkingSpot(ParkingSpot parkingSpot);

}
