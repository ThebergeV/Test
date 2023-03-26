package ca.mcgill.ecse321.parkinglotapplication.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.BillRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;
import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;

@SpringBootTest
public class BillTests {

	@Autowired
	private BillRepository billRepository;

	@Autowired 
	private NonMonthlyUserRepository nonMonthlyUserRepository;

	@AfterEach
	public void clearDatabase() {
		nonMonthlyUserRepository.deleteAll();
		billRepository.deleteAll();
	}
	
	@Test
	public void testBill() {

		//Create nonMonthlyUser
		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser();

		String name = "Racer X";
		String email = "definetly.not.rexracer@email.com";
		String number = "9";
        String licensePlateNumber = "RAC3RX";
		String password = "moo";
        
		nonMonthlyUser.setName(name);
		nonMonthlyUser.setEmail(email);
		nonMonthlyUser.setNumber(number);
        nonMonthlyUser.setLicensePlateNumber(licensePlateNumber);
		nonMonthlyUser.setPassword(password);
		
		//save the user
		nonMonthlyUser= nonMonthlyUserRepository.save(nonMonthlyUser);

		//create the bill
		Bill bill = new Bill();
		int price =44;
		Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
		boolean isPaid = true;
		CustomerType customerType = CustomerType.AccountHolder;
		
		bill.setAccountHolder(nonMonthlyUser);
		bill.setPrice(price);
		bill.setDate(date);
		bill.setIsPaid(isPaid);
		bill.setCustomerType(customerType);

		//Save Object
		bill = billRepository.save(bill);
		
		//Read object from database
		bill = billRepository.findBillByTransactionID(bill.getTransactionID());

		//Assert that object has correct attributes
		assertNotNull(bill);
		assertEquals(price, bill.getPrice());
		assertEquals(date, bill.getDate());
		assertEquals(isPaid, bill.getIsPaid());
		assertEquals(customerType, bill.getCustomerType());
		}
	
	@Test
	void testDeleteBill(){
		//Create nonMonthlyUser
		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser();

		String name = "Racer X";
		String email = "definetly.not.rexracer@email.com";
		String number = "9";
        String licensePlateNumber = "RAC3RX";
        String password = "moo";
        
		nonMonthlyUser.setName(name);
		nonMonthlyUser.setEmail(email);
		nonMonthlyUser.setNumber(number);
        nonMonthlyUser.setLicensePlateNumber(licensePlateNumber);
		nonMonthlyUser.setPassword(password);
		
		//save the user
		nonMonthlyUser= nonMonthlyUserRepository.save(nonMonthlyUser);

		//create the bill
		Bill bill = new Bill();
		int price = 44;
		Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
		boolean isPaid = true;
		CustomerType customerType = CustomerType.AccountHolder;
		
		bill.setAccountHolder(nonMonthlyUser);
		bill.setPrice(price);
		bill.setDate(date);
		bill.setIsPaid(isPaid);
		bill.setCustomerType(customerType);

		//Save Object
		bill = billRepository.save(bill);
		
		//Read object from database
		bill = billRepository.findBillByTransactionID(bill.getTransactionID());

		//Delete object from database
		billRepository.delete(bill);

		//Assert that database doesn't have object
		assertNull(billRepository.findBillByTransactionID(bill.getTransactionID()));

	}
}

