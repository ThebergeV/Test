package ca.mcgill.ecse321.parkinglotapplication.dto;


import ca.mcgill.ecse321.parkinglotapplication.model.Employee;


public class EmployeeRequestDto {

    private String name; 
    private String password;
    private String number;
    private String email;



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
	
	public Employee toModel() {

		Employee employee = new Employee(name, email, number, password);
        
		return employee;
	}


    
}
