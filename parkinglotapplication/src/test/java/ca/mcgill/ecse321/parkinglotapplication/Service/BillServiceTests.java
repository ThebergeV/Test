package ca.mcgill.ecse321.parkinglotapplication.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDateTime;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.parkinglotapplication.dao.BillRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;
import ca.mcgill.ecse321.parkinglotapplication.service.BillService;

@SpringBootTest
public class BillServiceTests {

    //create a fake repository
    @Mock
	private BillRepository billRepository;


    // Autowire bill services with the fake repository 
	@InjectMocks
	private BillService billService;

    @Test
    public void testGetBillByValidId(){
        //set up mock
        final int id = 1;
		final double price = 18;
        final Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
        final CustomerType customerType = CustomerType.NonAccountHolder;
        final boolean isPaid = false;
        
        final String name = "Rick";
        final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final Employee rick = new Employee(name, email, number, password);
        
        final Bill bill = new Bill(price, date, customerType, isPaid, rick);
        when(billRepository.findBillByTransactionID(id)).thenReturn(bill);

        //call comoponent under test 
        Bill bill2 = billService.getBillByID(id);

        assertNotNull(bill2);
        assertEquals(price, bill2.getPrice());
        assertEquals(date, bill2.getDate());
        assertEquals(customerType, bill2.getCustomerType());
        assertEquals(isPaid, bill2.getIsPaid());
        assertEquals(rick, bill2.getAccountHolder());
    }

    @Test
	public void testGetBillByInvalidId() {
		final int invalidId = 12;
		when(billRepository.findBillByTransactionID(invalidId)).thenReturn(null);

		ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> billService.getBillByID(invalidId));
		assertEquals("Bill not found", e.getMessage());
	}


    @Test
	public void testCreateValidBill() {
		//set up mock 
        final double price = 18;
        final Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
        final CustomerType customerType = CustomerType.NonAccountHolder;
        final boolean isPaid = false;
        
        final String name = "Rick";
        final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final Employee rick = new Employee(name, email, number, password);
         
        final Bill bill = new Bill(price, date, customerType, isPaid, rick);
		when(billRepository.save(bill)).thenReturn(bill);

		Bill bill2 = billService.createBill(bill);
		
		assertNotNull(bill2);
        assertEquals(price, bill2.getPrice());
        assertEquals(date, bill2.getDate());
        assertEquals(customerType, bill2.getCustomerType());
        assertEquals(isPaid, bill2.getIsPaid());
        assertEquals(rick, bill2.getAccountHolder());

		// personRepo.save() should be called exactly once if it was saved to database
		verify(billRepository, times(1)).save(bill);
	}

    @Test
	public void CreateInvalidBill() {
        //set up mock
        final double price = 18;
        final Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
        final CustomerType customerType = CustomerType.AccountHolder;
        final boolean isPaid = false;
        
        final String name = "Rick";
        final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final Employee rick = new Employee(name, email, number, password);
        final Bill bill = new Bill(price, date, customerType, isPaid, rick);

		ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> billService.createBill(bill));

		assertEquals("Bill for an account holder cannot be associated with an employee", e.getMessage());
	}


    @Test
	public void testUpdateBill() {
		//set up mock 
        final double price = 18;
        final Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
        final CustomerType customerType = CustomerType.NonAccountHolder;
        final boolean isPaid = false;
        
        final String name = "Rick";
        final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final Employee rick = new Employee(name, email, number, password);
         
        final Bill bill = new Bill(price, date, customerType, isPaid, rick);
		Bill bill2 = new Bill(19, date, customerType, true, rick);
        when(billRepository.save(bill)).thenReturn(bill2);

        int id = bill.getTransactionID();
		bill2 = billService.updateBill(id, bill);
		
		assertNotNull(bill2);
        assertEquals(19, bill2.getPrice());
        assertEquals(date, bill2.getDate());
        assertEquals(customerType, bill2.getCustomerType());
        assertEquals(true, bill2.getIsPaid());
        assertEquals(rick, bill2.getAccountHolder());

		// personRepo.save() should be called exactly once if it was saved to database
		verify(billRepository, times(1)).save(bill);
	}
}
