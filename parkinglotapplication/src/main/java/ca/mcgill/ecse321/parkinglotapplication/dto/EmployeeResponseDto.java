package ca.mcgill.ecse321.parkinglotapplication.dto;

import ca.mcgill.ecse321.parkinglotapplication.model.Employee;

public class EmployeeResponseDto {

	private int accountId;
    private String name; 
    private String password;
    private String number;
    private String email;

    EmployeeResponseDto() {}

    public EmployeeResponseDto(Employee employee){
        
            this.accountId = employee.getAccountId();
            this.name = employee.getName();
            this.password = employee.getPassword();
            this.number = employee.getNumber();
            this.email = employee.getEmail();
        }

        public int getAccountId() {
            return accountId;
        }
        
        public String getName() {
            return name;
        }
        
        public String getPassword() {
            return password;
        }
        
        public String getNumber() {
            return number;
        }

        public String getEmail() {
            return email;
        }
        
        
    }


