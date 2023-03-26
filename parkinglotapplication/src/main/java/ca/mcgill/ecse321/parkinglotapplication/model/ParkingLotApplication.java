package ca.mcgill.ecse321.parkinglotapplication.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



// line 70 "model.ump"
// line 163 "model.ump"
@Entity
public class ParkingLotApplication
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Floor { ONE, TWO, THREE, FOUR, FIVE }
  public enum CustomerType { AccountHolder, NonAccountHolder }
  public enum Status { Completed, InProgress }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ParkingLotApplication Attributes
  private Time openTime;
  private Time closeTime;
  private double monthlyFee;
  private double hourlyFee;
  private int applicationID;


  //------------------------
  // CONSTRUCTOR
  //------------------------
  public ParkingLotApplication() {}
  public ParkingLotApplication(Time aOpenTime, Time aCloseTime, double aMonthlyFee, double aHourlyFee, int aApplicationID)
  {
    openTime = aOpenTime;
    closeTime = aCloseTime;
    monthlyFee = aMonthlyFee;
    hourlyFee = aHourlyFee;
    applicationID = aApplicationID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpenTime(Time aOpenTime)
  {
    boolean wasSet = false;
    openTime = aOpenTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCloseTime(Time aCloseTime)
  {
    boolean wasSet = false;
    closeTime = aCloseTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setMonthlyFee(double aMonthlyFee)
  {
    boolean wasSet = false;
    monthlyFee = aMonthlyFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setHourlyFee(double aHourlyFee)
  {
    boolean wasSet = false;
    hourlyFee = aHourlyFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setApplicationID(int aApplicationID)
  {
    boolean wasSet = false;
    applicationID = aApplicationID;
    wasSet = true;
    return wasSet;
  }

  public Time getOpenTime()
  {
    return openTime;
  }

  public Time getCloseTime()
  {
    return closeTime;
  }

  public double getMonthlyFee()
  {
    return monthlyFee;
  }

  public double getHourlyFee()
  {
    return hourlyFee;
  }
  @Id
  @GeneratedValue
  public int getApplicationID()
  {
    return applicationID;
  }

}