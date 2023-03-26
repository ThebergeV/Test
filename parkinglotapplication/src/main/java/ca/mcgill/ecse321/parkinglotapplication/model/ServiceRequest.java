package ca.mcgill.ecse321.parkinglotapplication.model;

import java.sql.Date;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

// line 96 "model.ump"
// line 173 "model.ump"
@Entity
public class ServiceRequest
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Completed, InProgress }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceRequest Attributes
  private Date date;
  private Status status;
  private int serviceRequestID;

  //ServiceRequest Associations
  private Services services;
  private Bill bill;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public ServiceRequest () {}
  public ServiceRequest(Date aDate, Status aStatus, Services aServices, Bill aBill)
  {
    date = aDate;
    status = aStatus;
    //serviceRequestID = aServiceRequestID;
    if (!setServices(aServices))
    {
      throw new RuntimeException("Unable to create ServiceRequest due to aService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setBill(aBill))
    {
      throw new RuntimeException("Unable to create ServiceRequest due to aBill. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setServiceRequestID(int aServiceRequestID)
  {
    boolean wasSet = false;
    serviceRequestID = aServiceRequestID;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Status getStatus()
  {
    return status;
  }
  
  @Id
  @GeneratedValue
  public int getServiceRequestID()
  {
    return serviceRequestID;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  @NotFound(action = NotFoundAction.IGNORE)
  public Services getServices()
  {
    return services;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  @NotFound(action = NotFoundAction.IGNORE)
  public Bill getBill()
  {
    return bill;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setServices(Services aNewServices)
  {
    boolean wasSet = false;
    if (aNewServices != null)
    {
      services = aNewServices;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setBill(Bill aNewBill)
  {
    boolean wasSet = false;
    if (aNewBill != null)
    {
      bill = aNewBill;
      wasSet = true;
    }
    return wasSet;
  }

}