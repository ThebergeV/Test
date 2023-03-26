package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/




// line 50 "model.ump"
// line 148 "model.ump"
@Entity
public class MonthlyUser extends Customer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MonthlyUser Associations
  @OneToOne(cascade = CascadeType.ALL)
  private ParkingSpot parkingSpot;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public MonthlyUser() {}
  public MonthlyUser(String aName, String aEmail, String aNumber, String aPassword, String aLicensePlateNumber, ParkingSpot aParkingSpot)
  {
    super(aName, aEmail, aNumber, aPassword, aLicensePlateNumber);
    
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public ParkingSpot getParkingSpot()
  {
    return parkingSpot;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setParkingSpot(ParkingSpot aNewParkingSpot)
  {
    boolean wasSet = false;
    if (aNewParkingSpot != null)
    {
      parkingSpot = aNewParkingSpot;
      wasSet = true;
    }
    return wasSet;
  }

}