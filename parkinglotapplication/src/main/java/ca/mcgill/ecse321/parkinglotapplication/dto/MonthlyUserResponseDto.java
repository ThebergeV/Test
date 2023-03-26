package ca.mcgill.ecse321.parkinglotapplication.dto;

import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;

public class MonthlyUserResponseDto {

	private int accountId;
    private String name; 
    private String password;
    private String number;
    private String email;
    private String licensePlateNumber;
    private ParkingSpot parkingSpot;

    MonthlyUserResponseDto() {}

    public MonthlyUserResponseDto(MonthlyUser monthlyUser){
        
            this.accountId = monthlyUser.getAccountId();
            this.name = monthlyUser.getName();
            this.password = monthlyUser.getPassword();
            this.number = monthlyUser.getNumber();
            this.email = monthlyUser.getEmail();
            this.licensePlateNumber = monthlyUser.getLicensePlateNumber();
            this.parkingSpot = monthlyUser.getParkingSpot();

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
        public ParkingSpot getParkingSpot() {
            return parkingSpot;
        }
        
    }
