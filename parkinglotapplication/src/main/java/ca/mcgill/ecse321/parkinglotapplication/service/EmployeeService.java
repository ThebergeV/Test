package ca.mcgill.ecse321.parkinglotapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotapplication.dao.EmployeeRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.Employee;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    /**
	 * Gets a list of all the employees 
	 *
	 * @return List of employees
	 */
    @Transactional
    public Iterable<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    /**
	 * Logs in user as a specific employee.
	 *
	 * @param email and password of account
	 * @return The employee account with the ID
	 */
    @Transactional
    public Employee logIn(String email, String password) {
		// Find by username
		Employee account = employeeRepository.findEmployeeByEmail(email);
		if (account == null) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Invalid Email.");
		}
        if (password != account.getPassword()){
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Invalid password, please try again.");
        }
        return account;
    }

    /**
	 * Gets employee with given ID.
	 *
	 * @param employee accountID
	 * @return The employee account with the ID
	 */
    @Transactional
    public Employee getEmployeeByID(int id){
        Employee employee = employeeRepository.findEmployeeByAccountId((id));
        if (employee == null){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND,"Employee not found.");
        }
        return employee;
    }
    
    /**
	 * Create a new employee 
	 *
	 * @param Name, email, number, password 
	 * @return A new employee
	 */
    @Transactional
	public Employee createEmployee(Employee employee) {

    // check to see if fields are empty
    if (employee.getName() == null || employee.getEmail() == null || employee.getNumber() == null || employee.getPassword()==null ) {
        throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "One or many feilds are empty, please fill in the correct information");
      }
      if (employee.getEmail().isBlank()) {
        throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Email cannot be empty");      
    } else { // if the email contains any spaces, show error
        if (employee.getEmail().contains(" ")) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Email cannot contain spaces");
        } // check the validity of email according to constraints
        if (!((employee.getEmail().indexOf("@") > 0 && employee.getEmail().indexOf("@") == employee.getEmail().lastIndexOf("@") && employee.getEmail().indexOf("@")
            < employee.getEmail().lastIndexOf(".") - 1 && employee.getEmail().lastIndexOf(".") < employee.getEmail().length() - 1))){
                throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Invalid Email");
            }
      }
		return employeeRepository.save(employee);
    
	}

    /**
	 * Finds the employee using their email  
	 *
	 * @param email  
	 * @return Employee of given email 
	 */
    @Transactional
	public Employee getEmployeeByEmail(String email){
		Employee employee = employeeRepository.findEmployeeByEmail(email);
	
		if (employee == null) {
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND,"Employee not found.");
		}
		
		return employee;
	}

	/**
	 * Deletes an employee using their email  
	 *
	 * @param email  
	 * @return Employee of deleted account
	 */
    @Transactional
    public Employee deleteEmployee(String email){ 
        Employee employee = employeeRepository.findEmployeeByEmail(email);

        if (employee == null){
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND,"Employee not found.");
        }
        
        employeeRepository.delete(employee);

        return employee;

    }

    //reserves a parking spot chosen by a Monthly user
    @Transactional
    public ParkingSpot reserveNonAccountUsersParkingSpot(ParkingSpot aParkingSpot, int employeeAccountId) {
        Employee aEmployee = employeeRepository.findEmployeeByAccountId(employeeAccountId);
        if (aEmployee == null) {
            throw new ParkingLotApplicationException(HttpStatus.NOT_FOUND,"Employee not found");
        }
        if (aParkingSpot.getFloor()!=Floor.TWO && aParkingSpot.getFloor()!=Floor.THREE) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Parking Spot not for NonAccountUsers");
        }
        if (aParkingSpot.getIsAvailable() == false) {
            throw new ParkingLotApplicationException(HttpStatus.BAD_REQUEST, "Parking Spot not available");
        }
        aParkingSpot.setIsAvailable(false);
        return parkingSpotRepository.save(aParkingSpot);
    }

}
    