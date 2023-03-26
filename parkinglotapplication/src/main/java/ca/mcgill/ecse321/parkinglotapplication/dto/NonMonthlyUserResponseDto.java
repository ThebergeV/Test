package ca.mcgill.ecse321.parkinglotapplication.dto;

import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;

public class NonMonthlyUserResponseDto {

	private int accountId;
    private String name; 
    private String password;
    private String number;
    private String email;
    private String licensePlateNumber;

    NonMonthlyUserResponseDto() {}

    public NonMonthlyUserResponseDto(NonMonthlyUser nonMonthlyUser){
        
            this.accountId = nonMonthlyUser.getAccountId();
            this.name = nonMonthlyUser.getName();
            this.password = nonMonthlyUser.getPassword();
            this.number = nonMonthlyUser.getNumber();
            this.email = nonMonthlyUser.getEmail();
            this.licensePlateNumber = nonMonthlyUser.getLicensePlateNumber();

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
        public String getLicensePlateNumber() {
            return licensePlateNumber;
        }
        
    }
