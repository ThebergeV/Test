package ca.mcgill.ecse321.parkinglotapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotapplication.dto.ParkingLotApplicationRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.ParkingLotApplicationResponseDto;

import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingLotApplicationService;


@RestController
public class ParkingLotApplicationController {
    
    @Autowired
	private ParkingLotApplicationService parkingLotApplicationService;

     /**
	 * Gets a specific application.
	 *
	 * @param id The primary key of the application to look up.
	 * @return The application with the given ID.
	 */
    @GetMapping("/application/{id}")
	public ResponseEntity<ParkingLotApplicationResponseDto> getParkingLotApplicationByID(@PathVariable int id) {
		ParkingLotApplication application = parkingLotApplicationService.getParkingLotApplicationByID(id);
		ParkingLotApplicationResponseDto responseBody = new ParkingLotApplicationResponseDto(application);
		return new ResponseEntity<ParkingLotApplicationResponseDto>(responseBody, HttpStatus.OK);
	}

	/**
	 * @param applicationDto The application to create.
	 * @return The created application.
	 */
	@PostMapping("/application")
	public ResponseEntity<ParkingLotApplicationResponseDto> createParkingLotApplication(@RequestBody ParkingLotApplicationRequestDto applicationDto) {
		ParkingLotApplication application = applicationDto.toModel();
		application = parkingLotApplicationService.createParkingLotApplication(application);
		ParkingLotApplicationResponseDto responseBody = new ParkingLotApplicationResponseDto(application);
		return new ResponseEntity<ParkingLotApplicationResponseDto>(responseBody, HttpStatus.CREATED);
	}

	/**
	 * @param applicationDto the application to update along with its id.
	 * @return The updated application.
	 */
	@PutMapping("/application/{id}")
	public ResponseEntity<ParkingLotApplicationResponseDto> updateParkingLotApplication(@PathVariable int id, @RequestBody ParkingLotApplicationRequestDto applicationDto) {
		ParkingLotApplication application = applicationDto.toModel();
		application.setApplicationID(id);
		application = parkingLotApplicationService.updateParkingLotApplication(application);
		ParkingLotApplicationResponseDto responseBody = new ParkingLotApplicationResponseDto(application);
		return new ResponseEntity<ParkingLotApplicationResponseDto>(responseBody, HttpStatus.OK);
	}

}