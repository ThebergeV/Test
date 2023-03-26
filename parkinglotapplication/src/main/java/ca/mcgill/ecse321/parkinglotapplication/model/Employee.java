package ca.mcgill.ecse321.parkinglotapplication.model;

import javax.persistence.Entity;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

// Every model class needs a default (no args) constructor. It can be private.

@Entity
public class Employee extends AccountHolder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Employee() {}
  public Employee(String aName, String aEmail, String aNumber, String aPassword)
  {
    super(aName, aEmail, aNumber, aPassword);
  }

}