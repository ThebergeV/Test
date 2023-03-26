package ca.mcgill.ecse321.parkinglotapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotapplication.dto.BillRequestDto;
import ca.mcgill.ecse321.parkinglotapplication.dto.BillResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.service.BillService;


@RestController
public class BillController {

    @Autowired
	private BillService billService;

    /**
	 * Gets a specific bill.
	 *
	 * @param transactionId The primary key of the bill to look up.
	 * @return The bill with the given ID.
	 */
    @GetMapping("/bill/{transactionId}")
	public ResponseEntity<BillResponseDto> getBillById(@PathVariable("transactionId") int transactionId) {
		Bill bill = billService.getBillByID(transactionId);
		BillResponseDto responseBody = new BillResponseDto(bill);
		return new ResponseEntity<BillResponseDto>(responseBody, HttpStatus.OK);
	}
	

	
    /**
	 * Creates a bill
	 * 
	 * @param billDto The bill to create.
	 * @return The created bill.
	 */
	@PostMapping("/bill")
	public ResponseEntity<BillResponseDto> createBill(@RequestBody BillRequestDto billDto) {
		AccountHolder accountHolder = billDto.getAccountHolder();
		Bill bill = billDto.toModel();
		bill.setAccountHolder(accountHolder);
		bill = billService.createBill(bill);
		BillResponseDto responseBody = new BillResponseDto(bill);
		return new ResponseEntity<BillResponseDto>(responseBody, HttpStatus.CREATED);
	}

	/**
	 * Deletes a bill
	 * 
	 * @param transactionId The transactionId of the bill to delete.
	 * @return The deleted bill.
	 */
	@DeleteMapping("/bill/{tansactionId}")
	public ResponseEntity<BillResponseDto> deleteBill(@PathVariable int transactionId) {
		Bill bill = billService.deleteBillByTransactionID(transactionId);
		BillResponseDto responseBody = new BillResponseDto(bill);
		return new ResponseEntity<BillResponseDto>(responseBody, HttpStatus.OK);
	}
}
