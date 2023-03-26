package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

// line 62 "model.ump"
// line 158 "model.ump"
@Entity
public class ParkingSpot
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Floor { ONE, TWO, THREE, FOUR, FIVE }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ParkingSpot Attributes
  private Floor floor;
  @Id
  @GeneratedValue
  private int parkingID;
  private boolean isAvailable;
  

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public ParkingSpot () {}
  public ParkingSpot(Floor aFloor, boolean aIsAvailable)
  {
    floor = aFloor;
    isAvailable = aIsAvailable;
  
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFloor(Floor aFloor)
  {
    boolean wasSet = false;
    floor = aFloor;
    wasSet = true;
    return wasSet;
  }
  
  public boolean setIsAvailable(boolean aIsAvailable)
  {
    boolean wasSet = false;
    isAvailable = aIsAvailable;
    wasSet = true;
    return wasSet;
  }

  public Floor getFloor()
  {
    return floor;
  }

  public int getParkingID()
  {
    return parkingID;
  }

  public boolean getIsAvailable()
  {
    return isAvailable;
  }

}

