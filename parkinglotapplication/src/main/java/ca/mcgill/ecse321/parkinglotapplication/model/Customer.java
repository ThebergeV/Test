package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Customer extends AccountHolder
{

  //Customer Attributes
  private String licensePlateNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Customer() {}
  public Customer(String aName, String aEmail, String aNumber, String aPassword, String aLicensePlateNumber)
  {
    super(aName, aEmail, aNumber, aPassword);
    licensePlateNumber = aLicensePlateNumber;
  }

  public boolean setLicensePlateNumber(String aLicensePlateNumber)
  {
    boolean wasSet = false;
    licensePlateNumber = aLicensePlateNumber;
    wasSet = true;
    return wasSet;
  }

  public String getLicensePlateNumber()
  {
    return licensePlateNumber;
  }

  public String toString()
  {
    return super.toString() + "["+
            "licensePlateNumber" + ":" + getLicensePlateNumber()+ "]";
  }
}