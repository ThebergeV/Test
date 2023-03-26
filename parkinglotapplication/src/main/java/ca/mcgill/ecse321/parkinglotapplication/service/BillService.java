package ca.mcgill.ecse321.parkinglotapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ca.mcgill.ecse321.parkinglotapplication.dao.BillRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    /**
	 * Gets a list of all the bills 
	 *
	 * @return List of bills
	 */
    @Transactional
    public Iterable<Bill> getAllBills(){
        return billRepository.findAll();

    }

    /**
	 * Gets a bill by its transactionId 
	 *
     * @param id
	 * @return A bill
	 */
    @Transactional
    public Bill getBillByID(int id){
        Bill bill = billRepository.findBillByTransactionID(id);
        if (bill == null){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND, "Bill not found");
        }
        return bill;
    }

    /**
	 * Creates a bill 
	 *
     * @param bill
	 * @return a created Bill
	 */
    @Transactional
	public Bill createBill(Bill bill) {
        // check to see if fields are empty
        if (bill.getCustomerType() == null || bill.getDate() == null || bill.getAccountHolder() == null){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"One or many feilds are empty, please fill in the correct information");
        }
        if (bill.getCustomerType() == CustomerType.NonAccountHolder && bill.getAccountHolder().getClass() != Employee.class){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Bill for non account holder must be associated with an employee");
        }
        if (bill.getCustomerType() == CustomerType.AccountHolder && bill.getAccountHolder().getClass() == Employee.class){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Bill for an account holder cannot be associated with an employee");
        }
        bill.setIsPaid(false);
        
		return billRepository.save(bill);
	}

    /**
	 * Sets isPaid to true for bill
	 *
     * @param id
	 * @return Paid bill
	 */ 
    @Transactional
    public Bill updateBill(int transactionId, Bill bill){
        if (bill.getCustomerType() == null || bill.getDate() == null || bill.getAccountHolder() == null){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"One or many feilds are empty, please fill in the correct information");
        }
        if (bill.getCustomerType() == CustomerType.NonAccountHolder && bill.getAccountHolder().getClass() != Employee.class){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Bill for non account holder must be associated with an employee");
        }
        if (bill.getCustomerType() == CustomerType.AccountHolder && bill.getAccountHolder().getClass() == Employee.class){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST,"Bill for an account holder cannot be associated with an employee");
        }
        if (billRepository.existsById(transactionId)){
            billRepository.delete(bill);
            return billRepository.save(bill);
        }
        return billRepository.save(bill);
    }

    /**
	 * Deletes a bill
	 *
     * @param transactionId
	 * @return Deleted bill
	 */
    @Transactional
    public Bill deleteBillByTransactionID(int transactionId){ 
        Bill bill = billRepository.findBillByTransactionID(transactionId);

        if (bill == null){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND,"Not found.");
        }
        billRepository.deleteByTransactionID(bill.getTransactionID());

        return bill;
    }

    

}
    