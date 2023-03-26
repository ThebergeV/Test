package ca.mcgill.ecse321.parkinglotapplication.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

// line 84 "model.ump"
// line 168 "model.ump"
@Entity
public class Bill
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum CustomerType { AccountHolder, NonAccountHolder }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bill Attributes
  private double price;
  private Date date;
  private CustomerType customerType;
  private boolean isPaid;
  @Id
  @GeneratedValue
  private int transactionID;

  //Bill Associations
  private AccountHolder accountHolder;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bill() {}
  public Bill(double aPrice, Date aDate, CustomerType aCustomerType, boolean aIsPaid, AccountHolder aAccountHolder)
  {
    price = aPrice;
    date = aDate;
    customerType = aCustomerType;
    isPaid = aIsPaid;
    //transactionID = aTransactionID;
    if (!setAccountHolder(aAccountHolder))
    {
      throw new RuntimeException("Unable to create Bill due to aAccountHolder. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCustomerType(CustomerType aCustomerType)
  {
    boolean wasSet = false;
    customerType = aCustomerType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPaid(boolean aIsPaid)
  {
    boolean wasSet = false;
    isPaid = aIsPaid;
    wasSet = true;
    return wasSet;
  }
  
  public boolean setTransactionID(int aTransactionID)
  {
    boolean wasSet = false;
    transactionID = aTransactionID;
    wasSet = true;
    return wasSet;
  }

  public double getPrice()
  {
    return price;
  }

  public Date getDate()
  {
    return date;
  }

  public CustomerType getCustomerType()
  {
    return customerType;
  }

  public boolean getIsPaid()
  {
    return isPaid;
  }
  @Id
  @GeneratedValue 
  public int getTransactionID()
  {
    return transactionID;
  }


  /* Code from template association_GetOne */
  @ManyToOne
  @NotFound(action = NotFoundAction.IGNORE)
  public AccountHolder getAccountHolder()
  {
    return accountHolder;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAccountHolder(AccountHolder aNewAccountHolder)
  {
    boolean wasSet = false;
    if (aNewAccountHolder != null)
    {
      accountHolder = aNewAccountHolder;
      wasSet = true;
    }
    return wasSet;
  }
}