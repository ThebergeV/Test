package ca.mcgill.ecse321.parkinglotapplication.exception;

import org.springframework.http.HttpStatus;

public class ParkingLotApplicationException extends RuntimeException{
    

    private static final long serialVersionUID = 1L;

	private HttpStatus status;
	
	public ParkingLotApplicationException(HttpStatus status, String message) {
		
        super(message);
		
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
    
    
}