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

import ca.mcgill.ecse321.parkinglotapplication.dto.NonMonthlyUserRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.NonMonthlyUserResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.service.NonMonthlyUserService;


@RestController
public class NonMonthlyUserController {

    @Autowired
	private NonMonthlyUserService nonMonthlyUserService;

    /**
	 * Gets a specific nonMonthlyUser.
	 *
	 * @param id The primary key of the nonMonthlyUser to look up.
	 * @return The nonMonthlyUser with the given ID.
	 */
    @GetMapping("/nonmonthlyuser/{accountId}")
	public ResponseEntity<NonMonthlyUserResponseDto> getNonMonthlyUserById(@PathVariable int accountId) {
		NonMonthlyUser nonMonthlyUser = nonMonthlyUserService.getNonMonthlyUserByID(accountId);
		NonMonthlyUserResponseDto responseBody = new NonMonthlyUserResponseDto(nonMonthlyUser);
		return new ResponseEntity<NonMonthlyUserResponseDto>(responseBody, HttpStatus.OK);
	}
	
    /**
	 * @param nonMonthlyUserDto The nonMonthlyUser to create.
	 * @return The created nonMonthlyUser.
	 */
	@PostMapping("/nonmonthlyuser")
	public ResponseEntity<NonMonthlyUserResponseDto> createNonMonthlyUser(@RequestBody NonMonthlyUserRequestDto nonMonthlyUserDto) {
		NonMonthlyUser nonMonthlyUser = nonMonthlyUserDto.toModel();
		nonMonthlyUser = nonMonthlyUserService.createNonMonthlyUser(nonMonthlyUser);
		NonMonthlyUserResponseDto responseBody = new NonMonthlyUserResponseDto(nonMonthlyUser);
		return new ResponseEntity<NonMonthlyUserResponseDto>(responseBody, HttpStatus.CREATED);
	}

	// Update the license plate of a non-monthly user
	@PutMapping("/nonmonthlyuser/{accountId}")
	public ResponseEntity<NonMonthlyUserResponseDto> updateNonMonthlyUserLicensePlate(@PathVariable int accountId, @RequestBody NonMonthlyUserRequestDto nonMonthlyUserDto) {
		NonMonthlyUser nonMonthlyUser = nonMonthlyUserDto.toModel();
		//reset after toModel because account resets and accountID resets  
		nonMonthlyUser.setAccountId(accountId);
		nonMonthlyUser = nonMonthlyUserService.updateNonMonthlyUser(accountId, nonMonthlyUser);
		NonMonthlyUserResponseDto responseBody = new NonMonthlyUserResponseDto(nonMonthlyUser);
		return new ResponseEntity<NonMonthlyUserResponseDto>(responseBody, HttpStatus.OK);
	}


}

