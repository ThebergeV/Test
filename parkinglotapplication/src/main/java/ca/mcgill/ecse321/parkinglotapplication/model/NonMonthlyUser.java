package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

// line 32 "model.ump"
// line 133 "model.ump"
@Entity
public class NonMonthlyUser extends Customer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public NonMonthlyUser() {}
  public NonMonthlyUser(String aName, String aEmail, String aNumber, String aPassword,  String aLicensePlateNumber)
  {
    super(aName, aEmail, aNumber, aPassword, aLicensePlateNumber);
  }

}