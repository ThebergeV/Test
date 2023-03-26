package ca.mcgill.ecse321.parkinglotapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotapplication.dao.MonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

@Service //Add javadoc please
public class MonthlyUserService {

    @Autowired
    private MonthlyUserRepository monthlyUserRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public MonthlyUser getMonthlyUserByID(int id){
        MonthlyUser MonthlyUser = monthlyUserRepository.findMonthlyUserByAccountId((id));
        if (MonthlyUser == null){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Monthly User not found");
        }
        return MonthlyUser;
    }

    //creating a monthlyuser MAYBE ADD CHECKER FOR PARKINGSPOT 
    @Transactional
	public MonthlyUser createMonthlyUser(MonthlyUser monthlyUser) {
        if (monthlyUser.getName() == null || monthlyUser.getEmail() == null || monthlyUser.getNumber() == null || monthlyUser.getPassword()==null || monthlyUser.getLicensePlateNumber() == null || monthlyUser.getParkingSpot() == null) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"One or many feilds are empty, please fill in the correct information");
        }
        if (monthlyUser.getEmail().isBlank()) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Email cannot be empty");
        } else { // if the email contains any spaces, show error
            if (monthlyUser.getEmail().contains(" ")) {
                throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Email cannot contain space");
            } // check the validity of email according to constraints
            if (!((monthlyUser.getEmail().indexOf("@") > 0 && monthlyUser.getEmail().indexOf("@") == monthlyUser.getEmail().lastIndexOf("@") && monthlyUser.getEmail().indexOf("@")
                < monthlyUser.getEmail().lastIndexOf(".") - 1 && monthlyUser.getEmail().lastIndexOf(".") < monthlyUser.getEmail().length() - 1))){
                    throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Invalid email");            
            }
        }if (monthlyUser.getParkingSpot().getFloor()!=Floor.TWO && monthlyUser.getParkingSpot().getFloor()!=Floor.THREE) {
        throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Parking Spot not for Monthly Users");
        }
        ParkingSpot parkingSpot = parkingSpotRepository.save(monthlyUser.getParkingSpot());
        monthlyUser.setParkingSpot(parkingSpot);
        monthlyUser = monthlyUserRepository.save(monthlyUser);
        return monthlyUser;
	}

    // //delete a non monthly user by ID 
    // @Transactional
    // public MonthlyUser deleteMonthlyUser(int MonthlyUserId){ 
    //     MonthlyUser MonthlyUser = monthlyUserRepository.findMonthlyUserByAccountId(MonthlyUserId);

    //     if (MonthlyUser == null){
    //         throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND,"Monthly User not found");        
    //     }
    //     monthlyUserRepository.delete(MonthlyUser);

    //     return MonthlyUser;
    // }

    @Transactional
    public MonthlyUser updateMonthlyUser(int accountId, MonthlyUser monthlyUser){

        if (!monthlyUserRepository.existsById(accountId)){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Not found."); 
        }
        if (monthlyUser.getLicensePlateNumber() == null){
                throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"License plate invalid");
        }
        if (monthlyUser.getParkingSpot().getFloor()!=Floor.TWO && monthlyUser.getParkingSpot().getFloor()!=Floor.THREE) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Parking Spot not for Monthly Users");
        }
        ParkingSpot parkingSpot = parkingSpotRepository.save(monthlyUser.getParkingSpot());
        monthlyUser.setParkingSpot(parkingSpot);
        monthlyUser = monthlyUserRepository.save(monthlyUser);
        return monthlyUser;
    }

}
    