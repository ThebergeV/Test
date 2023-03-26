package ca.mcgill.ecse321.parkinglotapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingLotApplicationRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;

@Service
public class ParkingLotApplicationService {
    
    @Autowired
    private ParkingLotApplicationRepository parkinglotapplicationRepository;

    @Transactional
    public ParkingLotApplication updateParkingLotApplication(ParkingLotApplication application){
        if (!parkinglotapplicationRepository.existsById(application.getApplicationID())){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Not found."); 
        }
        if (application.getCloseTime() == null || application.getHourlyFee() == 0.0|| application.getOpenTime() == null|| application.getMonthlyFee() == 0.0){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "One or many feilds are empty, please fill in the correct information");
                }
        return parkinglotapplicationRepository.save(application);
    }  

    @Transactional
    public ParkingLotApplication getParkingLotApplicationByID(int id){
        ParkingLotApplication application = parkinglotapplicationRepository.findAppByApplicationID(id);
        if (application == null){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Not found.");        }
        return application;
    }

    @Transactional
	public ParkingLotApplication createParkingLotApplication(ParkingLotApplication application) {

        if (application.getCloseTime() == null || application.getHourlyFee() == 0.0|| application.getOpenTime() == null|| application.getMonthlyFee() == 0.0){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "One or many feilds are empty, please fill in the correct information");
                }
		return parkinglotapplicationRepository.save(application);
    
	}

}
