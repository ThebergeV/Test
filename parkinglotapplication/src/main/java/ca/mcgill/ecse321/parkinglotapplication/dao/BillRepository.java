package ca.mcgill.ecse321.parkinglotapplication.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;



public interface BillRepository extends CrudRepository<Bill, Integer>{
    
    //PRIMARY KEY
    Bill findBillByTransactionID(int transactionID);

    //delete bill
    void deleteByTransactionID(int transactionID);

    //delete all bills
    void deleteAll();

    //display all bills in db
    List<Bill> findAll();

    //get all bills for an account holder
    List<Bill> findByAccountHolderAccountId(AccountHolder accountHolder);
    

}
