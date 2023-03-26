package ca.mcgill.ecse321.parkinglotapplication.dto;


import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;


public class NonMonthlyUserRequestDto {

    private String name; 
    private String password;
    private String number;
    private String email;
    private String licensePlateNumber;


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
	
	public NonMonthlyUser toModel() {

		NonMonthlyUser nonMonthlyUser = new NonMonthlyUser(name, email, number, password, licensePlateNumber);
        
		return nonMonthlyUser;
	}
	
}
