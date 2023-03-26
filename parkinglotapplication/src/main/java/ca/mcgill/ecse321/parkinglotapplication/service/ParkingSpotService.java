package ca.mcgill.ecse321.parkinglotapplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.parkinglotapplication.dao.ParkingSpotRepository;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot;
import ca.mcgill.ecse321.parkinglotapplication.model.ParkingSpot.Floor;

@Service
public class ParkingSpotService {

    @Autowired

    private ParkingSpotRepository parkingSpotRepository;

    /**
     * <p>
     * initializes all parking spots
     * </p>
     * 
     * @return List of all the parking spots
     */
    @Transactional
    public Iterable<ParkingSpot> initializeAllParkingSpots() {
        List<ParkingSpot> allParkingSpots = new ArrayList<ParkingSpot>();
        // Initialize ground floor spots
        for(int i=0; i<70; i++) {
            ParkingSpot newParkingSpot = new ParkingSpot(Floor.ONE, true);
            allParkingSpots.add(parkingSpotRepository.save(newParkingSpot));
        }
        // Initialize other floor spots
        for(int j=0; j<4;j++) {
            Floor[] array= new Floor[]{Floor.TWO,Floor.THREE,Floor.FOUR,Floor.FIVE};
            for(int i=0; i<100; i++) {
                ParkingSpot newParkingSpot = new ParkingSpot(array[j], true);
                allParkingSpots.add(parkingSpotRepository.save(newParkingSpot));
            }
        }
        return allParkingSpots;
    }
    
    /**
	 * Frees up a previously unavailable ParkingSpot 
	 *
     * @param id
	 * @return A ParkingSpot
	 */
    @Transactional
    public ParkingSpot freeParkingSpot(int id) {
        ParkingSpot parkingSpot = parkingSpotRepository.findParkingByParkingID(id);
        if (parkingSpot.getIsAvailable()==true) {
            return parkingSpot;
        }
        if (parkingSpot.getFloor()==Floor.TWO || parkingSpot.getFloor()==Floor.THREE) {
            throw new IllegalArgumentException("Cannot free monthly user parking spot");
        }
        parkingSpot.setIsAvailable(true);
        return parkingSpot;
    }

    /**
     * <p>
     * gets all parking spots
     * </p>
     * 
     * @return List of all the parking spots
     */
    @Transactional
    public Iterable<ParkingSpot> getAllParkingSpots(){
        return parkingSpotRepository.findAll();
    }
    
    /**
     * <p>
     * gets the available parking spots for monthly users
     * </p>
     * 
     * @return List of the available parking spots for monthly users
     */
    @Transactional
    public Iterable<ParkingSpot> getAvailableParkingSpotsMonthly() {
        List<ParkingSpot> ParkingSpots = new ArrayList<ParkingSpot>();
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.TWO)) {
            if (k.getIsAvailable()==true) {
                ParkingSpots.add(k);
            }
        }
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.THREE)) {
            if (k.getIsAvailable()==true) {
                ParkingSpots.add(k);
            }
        }
        return ParkingSpots;
    }

     //reserves a parking spot chosen by a Monthly user 
     //i fixed this PLEASE DO NOT TOUCH
     @Transactional
     public void updateParkingSpot(ParkingSpot parkingSpot, Boolean isAvailable) {
        parkingSpot = parkingSpotRepository.findParkingByParkingID(parkingSpot.getParkingID());
        parkingSpot.setIsAvailable(isAvailable);
        parkingSpotRepository.save(parkingSpot);
        }
    /**
     * <p>
     * gets the unavailable parking spots for monthly users
     * </p>
     * 
     * @return List of the unavailable parking spots for monthly users
     */
    @Transactional
    public Iterable<ParkingSpot> getUnavailableParkingSpotsMonthly() {
        List<ParkingSpot> ParkingSpots = new ArrayList<ParkingSpot>();
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.TWO)) {
            if (k.getIsAvailable()==false) {
                ParkingSpots.add(k);
            }
        }
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.THREE)) {
            if (k.getIsAvailable()==false) {
                ParkingSpots.add(k);
            }
        }
        return ParkingSpots;
    }

    /**
     * <p>
     * gets the available parking spots for non-monthly users
     * </p>
     * 
     * @return List of the available parking spots for non-monthly users
     */    @Transactional
    public Iterable<ParkingSpot> getAvailableParkingSpotsNonMonthly() {
        List<ParkingSpot> ParkingSpots = new ArrayList<ParkingSpot>();
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.ONE)) {
            if (k.getIsAvailable()==true) {
                ParkingSpots.add(k);
            }
        }
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.FOUR)) {
            if (k.getIsAvailable()==true) {
                ParkingSpots.add(k);
            }
        }
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.FIVE)) {
            if (k.getIsAvailable()==true) {
                ParkingSpots.add(k);
            }
        }
        return ParkingSpots;
    }

    /**
     * <p>
     * gets the unavailable parking spots for non-monthly users
     * </p>
     * 
     * @return List of the unavailable parking spots for non-monthly users
     */
    @Transactional
    public Iterable<ParkingSpot> getUnavailableParkingSpotsNonMonthly() {
        List<ParkingSpot> ParkingSpots = new ArrayList<ParkingSpot>();
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.ONE)) {
            if (k.getIsAvailable()==false) {
                ParkingSpots.add(k);
            }
        }
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.FOUR)) {
            if (k.getIsAvailable()==false) {
                ParkingSpots.add(k);
            }
        }
        for(ParkingSpot k : parkingSpotRepository.findByFloor(Floor.FIVE)) {
            if (k.getIsAvailable()==false) {
                ParkingSpots.add(k);
            }
        }
        return ParkingSpots;
    }
}