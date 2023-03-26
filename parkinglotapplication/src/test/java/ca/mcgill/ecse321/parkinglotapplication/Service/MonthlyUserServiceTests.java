package ca.mcgill.ecse321.parkinglotapplication.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.MonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;
import ca.mcgill.ecse321.parkinglotapplication.service.MonthlyUserService;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingSpotService;

@SpringBootTest
public class MonthlyUserServiceTests {

    //create a fake repository
    @Mock
	private MonthlyUserRepository monthlyUserRepository;
    @Mock
	private ParkingSpotRepository parkingSpotRepository;

    // Autowire monthlyUser services with the fake repository 
	@InjectMocks
	private MonthlyUserService monthlyUserService;
    @InjectMocks
	private ParkingSpotService parkingSpotService;

    @Test
    public void testGetMonthlyUserByValidId(){
        //set up mock 
        final int id = 1;
		final String name = "Rick";
		final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final String licensePlateNumber = "12A BCD";
        final ParkingSpot parkingSpot = new ParkingSpot(Floor.THREE, true);

        final MonthlyUser rick = new MonthlyUser(name, email, number, password, licensePlateNumber, parkingSpot);
		when(monthlyUserRepository.findMonthlyUserByAccountId(id)).thenReturn(rick);

        //call comoponent under test 
        MonthlyUser monthlyUser = monthlyUserService.getMonthlyUserByID(id);

        assertNotNull(monthlyUser);
        assertEquals(name, monthlyUser.getName());
        assertEquals(email, monthlyUser.getEmail());
        assertEquals(password, monthlyUser.getPassword());
        assertEquals(number, monthlyUser.getNumber());
        assertEquals(licensePlateNumber, monthlyUser.getLicensePlateNumber());

    }

    @Test
	public void testGetMonthlyUserByInvalidId() {
		final int invalidId = 12;
		when(monthlyUserRepository.findMonthlyUserByAccountId(invalidId)).thenReturn(null);

        ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> monthlyUserService.getMonthlyUserByID(invalidId));

		assertEquals("Monthly User not found", e.getMessage());
	}

    @Test
	public void testCreateValidMonthlyUser() {
		final String name = "Rick";
		final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final String licensePlateNumber = "12A BCD";
        ParkingSpot parkingSpot = new ParkingSpot(Floor.THREE, true);

       parkingSpotRepository.save(parkingSpot);
        
        MonthlyUser rick = new MonthlyUser(name, email, number, password, licensePlateNumber, null);
        rick.setParkingSpot(parkingSpot);
		when(monthlyUserRepository.save(rick)).thenReturn(rick);

		MonthlyUser monthlyUser = monthlyUserService.createMonthlyUser(rick);
		
		assertNotNull(monthlyUser);
        assertEquals(name, monthlyUser.getName());
        assertEquals(email, monthlyUser.getEmail());
        assertEquals(password, monthlyUser.getPassword());
        assertEquals(number, monthlyUser.getNumber());
        assertEquals(licensePlateNumber, monthlyUser.getLicensePlateNumber());
        assertEquals(parkingSpot.getParkingID(), parkingSpot.getParkingID());

		// personRepo.save() should be called exactly once if it was saved to database
		verify(monthlyUserRepository, times(1)).save(rick);
	}

    @Test
	public void CreateInvalidMonthlyUser() {
        //set up mock 
        final String name = "Rick";
		final String password = null;
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final String licensePlateNumber = "12A BCD";

		final MonthlyUser rick = new MonthlyUser(name, email, number, password, licensePlateNumber, null);

		ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> monthlyUserService.createMonthlyUser(rick));

		assertEquals("One or many feilds are empty, please fill in the correct information", e.getMessage());
	}


}