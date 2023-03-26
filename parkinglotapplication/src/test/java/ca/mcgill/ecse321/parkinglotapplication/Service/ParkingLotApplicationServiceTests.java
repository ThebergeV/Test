package ca.mcgill.ecse321.parkinglotapplication.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingLotApplicationRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingLotApplication;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingLotApplicationService;

@SpringBootTest
public class ParkingLotApplicationServiceTests {

    //create a fake repository
    @Mock
	private ParkingLotApplicationRepository parkingLotApplicationRepository;


    // Autowire employee services with the fake repository 
	@InjectMocks
	private ParkingLotApplicationService parkingLotApplicationService;

    @Test
	public void testGetParkingLotApplication() {

        final int id = 1;
		final double hourlyFee1 = 250.3;
        final double monthlyFee1 = 100.6;
        final Time openTime1 = Time.valueOf("08:00:00");
        final Time closeTime1 = Time.valueOf("10:00:00");

        final ParkingLotApplication myApplication = new ParkingLotApplication(openTime1, closeTime1, monthlyFee1, hourlyFee1, id);

        when(parkingLotApplicationRepository.findAppByApplicationID(id)).thenReturn(myApplication);

        ParkingLotApplication application = parkingLotApplicationService.getParkingLotApplicationByID(id);

        assertNotNull(application);
        assertEquals(hourlyFee1, application.getHourlyFee());
        assertEquals(monthlyFee1, application.getMonthlyFee());
        assertEquals(openTime1, application.getOpenTime());
        assertEquals(closeTime1, application.getCloseTime());
	}

    @Test
	public void testCreateValidParkingLotApplication() {
		final int id = 7;
		final double hourlyFee = 150.5;
        final double monthlyFee = 3000.0;
        final Time openTime = Time.valueOf("07:00:00");
        final Time closeTime = Time.valueOf("14:00:00");
        final ParkingLotApplication myApplication = new ParkingLotApplication(openTime, closeTime, monthlyFee, hourlyFee, id);
		when(parkingLotApplicationRepository.save(myApplication)).thenReturn(myApplication);

		ParkingLotApplication application = parkingLotApplicationService.createParkingLotApplication(myApplication);
		
        assertNotNull(application);
        assertEquals(id, application.getApplicationID());
        assertEquals(hourlyFee, application.getHourlyFee());
        assertEquals(monthlyFee, application.getMonthlyFee());
        assertEquals(openTime, application.getOpenTime());
        assertEquals(closeTime, application.getCloseTime());

		// personRepo.save() should be called exactly once if it was saved to database
		verify(parkingLotApplicationRepository, times(1)).save(myApplication);
	}
    
}
