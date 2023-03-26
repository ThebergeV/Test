package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/




// line 56 "model.ump"
// line 153 "model.ump"
@Entity
public class Owner extends AccountHolder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Owner () {}
  public Owner(String aName, String aEmail, String aNumber, String aPassword)
  {
    super(aName, aEmail, aNumber, aPassword);
  }

}