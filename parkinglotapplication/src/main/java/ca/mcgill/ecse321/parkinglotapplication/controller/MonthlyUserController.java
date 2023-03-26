package ca.mcgill.ecse321.parkinglotapplication.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotapplication.dto.MonthlyUserRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.MonthlyUserResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.service.MonthlyUserService;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingSpotService;


@RestController
public class MonthlyUserController {

    @Autowired
	private MonthlyUserService monthlyUserService;

	@Autowired
	private ParkingSpotService parkingSpotService;

    /**
	 * Gets a specific monthlyUser.
	 *
	 * @param id The primary key of the monthlyUser to look up.
	 * @return The monthlyUser with the given ID.
	 */
    @GetMapping("/monthlyuser/{accountId}")
	public ResponseEntity<MonthlyUserResponseDto> getMonthlyUserById(@PathVariable int accountId) {
		MonthlyUser monthlyUser = monthlyUserService.getMonthlyUserByID(accountId);
		MonthlyUserResponseDto responseBody = new MonthlyUserResponseDto(monthlyUser);
		return new ResponseEntity<MonthlyUserResponseDto>(responseBody, HttpStatus.OK);
	}

    /**
	 * @param monthlyUserDto The monthlyUser to create.
	 * @return The created monthlyUser.
	 */
	@PostMapping("/monthlyuser")
	public ResponseEntity<MonthlyUserResponseDto> createMonthlyUser(@RequestBody MonthlyUserRequestDto monthlyUserDto) {
		ParkingSpot parkingSpot = monthlyUserDto.getParkingSpot();
		MonthlyUser monthlyUser = monthlyUserDto.toModel();
		//reset after toModel becasue goes null
		monthlyUser.setParkingSpot(parkingSpot);
		monthlyUser = monthlyUserService.createMonthlyUser(monthlyUser);
		//call creation of service request here!!! 
		MonthlyUserResponseDto responseBody = new MonthlyUserResponseDto(monthlyUser);
		return new ResponseEntity<MonthlyUserResponseDto>(responseBody, HttpStatus.CREATED);
	}

	// Update a non-monthly user's license plate and parking spot 
	@PutMapping("/monthlyuser/{accountId}")
	public ResponseEntity<MonthlyUserResponseDto> updateMonthlyUser(@PathVariable int accountId, @RequestBody MonthlyUserRequestDto monthlyUserDto) {
		ParkingSpot oldParkingSpot = monthlyUserDto.getParkingSpot();
		MonthlyUser monthlyUser = monthlyUserDto.toModel();
		//reset after toModel because account resets and accountID resets  
		monthlyUser.setParkingSpot(oldParkingSpot);
		monthlyUser.setAccountId(accountId);
		monthlyUser = monthlyUserService.updateMonthlyUser(accountId, monthlyUser);
		//update parking spots
		parkingSpotService.updateParkingSpot(oldParkingSpot, true);
		parkingSpotService.updateParkingSpot(monthlyUser.getParkingSpot(), false);
		MonthlyUserResponseDto responseBody = new MonthlyUserResponseDto(monthlyUser);
		return new ResponseEntity<MonthlyUserResponseDto>(responseBody, HttpStatus.OK);
	}

}