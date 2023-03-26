package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/




// line 37 "model.ump"
// line 138 "model.ump"
@Entity
public class ServiceType extends Services
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ServiceJob { TireChange, OilChange, Wash }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceType Attributes
  private ServiceJob serviceJob;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public ServiceType () {}
  public ServiceType(double aPrice, String aDescription, ServiceJob aServiceJob)
  {
    super(aPrice, aDescription);
    serviceJob = aServiceJob;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setServiceJob(ServiceJob aServiceJob)
  {
    boolean wasSet = false;
    serviceJob = aServiceJob;
    wasSet = true;
    return wasSet;
  }

  public ServiceJob getServiceJob()
  {
    return serviceJob;
  }

}