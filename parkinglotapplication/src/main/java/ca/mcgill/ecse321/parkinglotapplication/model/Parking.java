package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/




// line 43 "model.ump"
// line 143 "model.ump"
@Entity
public class Parking extends Services
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Parking Associations
  @ManyToOne(cascade = CascadeType.ALL)
  private ParkingSpot parkingSpot;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Parking() {}
  public Parking(double aPrice, String aDescription, ParkingSpot aParkingSpot)
  {
    super(aPrice, aDescription);
    if (!setParkingSpot(aParkingSpot))
    {
      throw new RuntimeException("Unable to create Parking due to aParkingSpot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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