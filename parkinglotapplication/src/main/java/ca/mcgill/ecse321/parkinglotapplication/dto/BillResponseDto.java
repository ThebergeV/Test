package ca.mcgill.ecse321.parkinglotapplication.dto;

import java.sql.Date;

import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;

public class BillResponseDto {

	private int transactionId;
    private Double price; 
    private Date date;
    private CustomerType customerType;
    private boolean isPaid;
    private AccountHolder accountHolder;

    BillResponseDto() {}

    public BillResponseDto(Bill bill){
        
            this.transactionId = bill.getTransactionID();
            this.price = bill.getPrice();
            this.date = bill.getDate();
            this.customerType = bill.getCustomerType();
            this.isPaid = bill.getIsPaid();
            this.accountHolder = bill.getAccountHolder();
        }

        public int getTransactionId() {
            return transactionId;
        }
        
        public Double getPrice() {
            return price;
        }
        
        public Date getDate() {
            return date;
        }
        
        public CustomerType getCustomerType() {
            return customerType;
        }

        public boolean getIsPaid() {
            return isPaid;
        }
        
        public AccountHolder getAccountHolder(){
            return accountHolder;
        }
    }


