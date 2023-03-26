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

import ca.mcgill.ecse321.parkinglotapplication.dao.NonMonthlyUserRepository;
import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.exception.ParkingLotApplicationException;
import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;
import ca.mcgill.ecse321.parkinglotapplication.service.NonMonthlyUserService;

@SpringBootTest
public class NonMonthlyUserServiceTests {

    //create a fake repository
    @Mock
	private NonMonthlyUserRepository nonMonthlyUserRepository;
    @Mock
	private ParkingSpotRepository parkingSpotRepository;


    // Autowire nonMonthlyUser services with the fake repository 
	@InjectMocks
	private NonMonthlyUserService nonMonthlyUserService;

    @Test
    public void testGetNonMonthlyUserByValidId(){
        //set up mock 
        final int id = 1;
		final String name = "Rick";
		final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final String licensePlateNumber = "12A BCD";

        final NonMonthlyUser rick = new NonMonthlyUser(name, email, number, password, licensePlateNumber);
		when(nonMonthlyUserRepository.findNonMonthlyUserByAccountId(id)).thenReturn(rick);

        //call comoponent under test 
        NonMonthlyUser nonMonthlyUser = nonMonthlyUserService.getNonMonthlyUserByID(id);

        assertNotNull(nonMonthlyUser);
        assertEquals(name, nonMonthlyUser.getName());
        assertEquals(email, nonMonthlyUser.getEmail());
        assertEquals(password, nonMonthlyUser.getPassword());
        assertEquals(number, nonMonthlyUser.getNumber());
        assertEquals(licensePlateNumber, nonMonthlyUser.getLicensePlateNumber());

    }

    @Test
	public void testGetNonMonthlyUserByInvalidId() {
		final int invalidId = 12;
		when(nonMonthlyUserRepository.findNonMonthlyUserByAccountId(invalidId)).thenReturn(null);

        ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
        () -> nonMonthlyUserService.getNonMonthlyUserByID(invalidId));

		assertEquals("Non-Monthly User not found", e.getMessage());
	}

    @Test
	public void testCreateValidNonMonthlyUser() {
		final String name = "Rick";
		final String password = "password123";
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final String licensePlateNumber = "12A BCD";

		final NonMonthlyUser rick = new NonMonthlyUser(name, email, number, password, licensePlateNumber);
		when(nonMonthlyUserRepository.save(rick)).thenReturn(rick);

		NonMonthlyUser nonMonthlyUser = nonMonthlyUserService.createNonMonthlyUser(rick);
		
		assertNotNull(nonMonthlyUser);
        assertEquals(name, nonMonthlyUser.getName());
        assertEquals(email, nonMonthlyUser.getEmail());
        assertEquals(password, nonMonthlyUser.getPassword());
        assertEquals(number, nonMonthlyUser.getNumber());
        assertEquals(licensePlateNumber, nonMonthlyUser.getLicensePlateNumber());

		// personRepo.save() should be called exactly once if it was saved to database
		verify(nonMonthlyUserRepository, times(1)).save(rick);
	}

    @Test
	public void CreateInvalidNonMonthlyUser() {
         //set up mock 
         final String name = "Rick";
		final String password = null;
        final String email = "Rick@mail.com";
        final String number = "514-999-8931";
        final String licensePlateNumber = "12A BCD";

		final NonMonthlyUser rick = new NonMonthlyUser(name, email, number, password, licensePlateNumber);

		ParkingLotApplicationException e = assertThrows(ParkingLotApplicationException.class,
				() -> nonMonthlyUserService.createNonMonthlyUser(rick));

		assertEquals("One or many feilds are empty, please fill in the correct information", e.getMessage());
	}

}

