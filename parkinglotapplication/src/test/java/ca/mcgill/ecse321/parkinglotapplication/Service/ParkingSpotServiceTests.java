package ca.mcgill.ecse321.parkinglotapplication.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingSpotService;

@SpringBootTest
public class ParkingSpotServiceTests {

    @Mock
    private ParkingSpotRepository parkingSpotRepository;
    
    @InjectMocks
    private ParkingSpotService parkingSpotService;

    @Test
    public void testFreeParkingSpot(){
        //set up mock 
        final Floor floor = Floor.FIVE;
        final boolean isAvailable = false;
        final int id = 1;
         
        final ParkingSpot parkingSpot = new ParkingSpot(floor, isAvailable);
        when(parkingSpotRepository.findParkingByParkingID(id)).thenReturn(parkingSpot);
        
		Iterable<ParkingSpot> parkingSpot2 = parkingSpotService.initializeAllParkingSpots();
		ParkingSpot parkingSpot3 = parkingSpot2.iterator().next();
    
        parkingSpot3 = parkingSpotService.freeParkingSpot(id);
        
        assertNotNull(parkingSpot3);
        assertEquals(true, parkingSpot3.getIsAvailable());
    }
}
