package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

// line 7 "model.ump"
// line 118 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AccountHolder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AccountHolder Attributes
  private String name;
  private String email;
  private String number; 
  private String password;
  @Id
  @GeneratedValue
  private int accountId;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public AccountHolder() {}
  public AccountHolder(String aName, String aEmail, String aNumber, String aPassword)
  {
    name = aName;
    email = aEmail;
    number = aNumber;
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }


  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setAccountId(int AaccountId)
  {
    boolean wasSet = false;
    accountId = AaccountId;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumber(String aNumber)
  {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }
  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public String getNumber()
  {
    return number;
  }
  public int getAccountId()
  {
    return accountId;
  }
  /* Code from template association_GetOne */

   
}