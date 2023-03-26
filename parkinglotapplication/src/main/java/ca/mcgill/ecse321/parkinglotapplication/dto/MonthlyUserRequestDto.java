package ca.mcgill.ecse321.parkinglotapplication.dto;


import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;


public class MonthlyUserRequestDto {

    private String name; 
    private String password;
    private String number;
    private String email;
    private String licensePlateNumber;
    private ParkingSpot parkingSpot;


    public String getName() {
		return name;
	}

    public String getEmail() {
		return email;
	}
	
	
	public String getPassword() {
		return password;
	}
	
    public String getNumber(){
        return number;
    }

	public String getLicensePlate(){
        return licensePlateNumber;
    }

    public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

    public void setNumber(String number){
        this.number = number;
    }
    public void setEmail(String email){
        this.email = email;
    }

	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public void setLicensePlate(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

    public void setParkingSpot(ParkingSpot parkingSpot){
        this.parkingSpot = parkingSpot;
    }
	
	public MonthlyUser toModel() {

		MonthlyUser monthlyUser = new MonthlyUser(name, email, number, password, licensePlateNumber, parkingSpot);
        
		return monthlyUser;
	}
	
}
