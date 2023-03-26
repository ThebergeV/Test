package ca.mcgill.ecse321.parkinglotapplication.dto;


import java.sql.Date;

import ca.mcgill.ecse321.parkinglotapplication.model.AccountHolder;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill;
import ca.mcgill.ecse321.parkinglotapplication.model.Bill.CustomerType;


public class BillRequestDto {

    private Double price; 
    private Date date;
    private CustomerType customerType;
    private boolean isPaid;
	private AccountHolder accountHolder;



    public Double getPrice() {
		return price;
	}

    public Date getDate() {
		return date;
	}
	
	
	public boolean getIsPaid() {
		return isPaid;
	}
	
    public CustomerType getCustomerType(){
        return customerType;
    }

	public AccountHolder getAccountHolder(){
		return accountHolder;
	}

    public void setprice(Double price){
        this.price = price;
    }
    public void setdate(Date date){
        this.date = date;
    }

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public void setAccountHolder(AccountHolder accountHolder){
		this.accountHolder = accountHolder;
	}
	
	public Bill toModel() {

		Bill bill = new Bill(price, date, customerType, isPaid, accountHolder);
        
		return bill;
	}


    
}
