package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 16 "model.ump"
// line 123 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Services
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private double price;
  private String description;
  @Id
  @GeneratedValue
  private int serviceID;


  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Services() {}
  public Services(double aPrice, String aDescription)
  {
    price = aPrice;
    description = aDescription;
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

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public double getPrice()
  {
    return price;
  }

  public String getDescription()
  {
    return description;
  }
  public int getServiceID()
  {
    return serviceID;
  }

}