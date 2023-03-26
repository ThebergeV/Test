package ca.mcgill.ecse321.parkinglotapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;

@Service //Add javadoc please
public class NonMonthlyUserService {

    @Autowired
    private NonMonthlyUserRepository nonMonthlyUserRepository;

    @Transactional
    public NonMonthlyUser getNonMonthlyUserByID(int id){
        NonMonthlyUser nonMonthlyUser = nonMonthlyUserRepository.findNonMonthlyUserByAccountId((id));
        if (nonMonthlyUser == null){;
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Non-Monthly User not found");
        }
        return nonMonthlyUser;
    }

    //creating a nonmonthlyuser

    @Transactional
	public NonMonthlyUser createNonMonthlyUser(NonMonthlyUser nonMonthlyUser) {

    if (nonMonthlyUser.getName() == null || nonMonthlyUser.getEmail() == null || nonMonthlyUser.getNumber() == null || nonMonthlyUser.getPassword()== null || nonMonthlyUser.getLicensePlateNumber() == null) {
        throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"One or many feilds are empty, please fill in the correct information");
      }
      if (nonMonthlyUser.getEmail().isBlank()) {
      throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Email cannot be empty");
      } else { // if the email contains any spaces, show error
        if (nonMonthlyUser.getEmail().contains(" ")) {
        throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Email cannot be contain spaces");
        } // check the validity of email according to constraints
        if (!((nonMonthlyUser.getEmail().indexOf("@") > 0 && nonMonthlyUser.getEmail().indexOf("@") == nonMonthlyUser.getEmail().lastIndexOf("@") && nonMonthlyUser.getEmail().indexOf("@")
            < nonMonthlyUser.getEmail().lastIndexOf(".") - 1 && nonMonthlyUser.getEmail().lastIndexOf(".") < nonMonthlyUser.getEmail().length() - 1))){
                throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Invalid Email");
            }
      } 
		return nonMonthlyUserRepository.save(nonMonthlyUser);
    
	}

    @Transactional
    public NonMonthlyUser updateNonMonthlyUser(int accountId, NonMonthlyUser nonMonthlyUser){

        if (!nonMonthlyUserRepository.existsById(accountId)){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Non-Monthly User not found"); 
        }
        if (nonMonthlyUser.getLicensePlateNumber() == null){
                throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"License plate invalid");
        }
        nonMonthlyUser = nonMonthlyUserRepository.save(nonMonthlyUser);
        return nonMonthlyUser;
    
    }
   
}
    