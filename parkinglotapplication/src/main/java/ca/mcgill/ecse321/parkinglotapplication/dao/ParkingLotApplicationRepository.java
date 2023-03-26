package ca.mcgill.ecse321.parkinglotapplication.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;

public interface ParkingLotApplicationRepository extends CrudRepository<ParkingLotApplication, Integer>{
    
    //PRIMARY KEY
    ParkingLotApplication findAppByApplicationID(int applicationID);
    
}
