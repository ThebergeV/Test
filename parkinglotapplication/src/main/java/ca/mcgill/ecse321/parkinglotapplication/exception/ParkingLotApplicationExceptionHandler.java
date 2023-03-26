package ca.mcgill.ecse321.parkinglotapplication.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParkingLotApplicationExceptionHandler {
    @ExceptionHandler(ParkingLotApplicationException.class)
	public ResponseEntity<String> handleEmployeeRegistrationException(ParkingLotApplicationException e) {
		return new ResponseEntity<String>(e.getMessage(), e.getStatus());
	}
    
}