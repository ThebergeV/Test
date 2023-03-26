package ca.mcgill.ecse321.parkinglotapplication.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.parkinglotapplication.dto.ParkingSpotResponseDto;
import ca.mcgill.ecse321.parkinglotapplication.service.ParkingSpotService;

@RestController
public class ParkingSpotController {
    @Autowired
    private ParkingSpotService parkingSpotService;

    /**
     * <p>
     * initializes all parking spots
     * </p>
     * 
     * @return List of all the parking spots
     */
    @PostMapping("/parkingSpot")
    public Iterable<ParkingSpotResponseDto> initializeAllParkingSpots() {
        return StreamSupport.stream(parkingSpotService.initializeAllParkingSpots().spliterator(), false)
				.map(e -> new ParkingSpotResponseDto(e))
				.collect(Collectors.toList());
    }
    
    /**
     * <p>
     * gets all parking spots
     * </p>
     * 
     * @return List of all the parking spots
     */
    @GetMapping("/parkingSpot")
    public Iterable<ParkingSpotResponseDto> getAllParkingSpots(){
        return StreamSupport.stream(parkingSpotService.getAllParkingSpots().spliterator(), false)
        .map(e -> new ParkingSpotResponseDto(e))
        .collect(Collectors.toList());
    }
    
    /**
     * <p>
     * frees a parking spot from the first, fourth and fifth floors
     * </p>
     * 
     * @param id parking Id
     * @return freed parking spot
     */
    //TODO
    //@
    //public ParkingSpot freeParkingSpot(int id) {
//        return parkingSpot;
    //}

    /**
     * <p>
     * gets the available parking spots for monthly users
     * </p>
     * 
     * @return List of the available parking spots for monthly users
     */
    @GetMapping("/parkingSpot/available/monthly")
    public Iterable<ParkingSpotResponseDto> getAvailableParkingSpotsMonthly() {
        return StreamSupport.stream(parkingSpotService.getAvailableParkingSpotsMonthly().spliterator(), false)
				.map(e -> new ParkingSpotResponseDto(e))
				.collect(Collectors.toList());
    }

    /**
     * <p>
     * gets the unavailable parking spots for monthly users
     * </p>
     * 
     * @return List of the unavailable parking spots for monthly users
     */
    @GetMapping("/parkingSpot/unavailable/monthly")
    public Iterable<ParkingSpotResponseDto> getUnavailableParkingSpotsMonthly() {
        return StreamSupport.stream(parkingSpotService.getUnavailableParkingSpotsMonthly().spliterator(), false)
				.map(e -> new ParkingSpotResponseDto(e))
				.collect(Collectors.toList());
    }

    /**
     * <p>
     * gets the available parking spots for non-monthly users
     * </p>
     * 
     * @return List of the available parking spots for non-monthly users
     */
    @GetMapping("/parkingSpot/available/nonmonthly")
    public Iterable<ParkingSpotResponseDto> getAvailableParkingSpotsNonMonthly() {
        return StreamSupport.stream(parkingSpotService.getAvailableParkingSpotsNonMonthly().spliterator(), false)
				.map(e -> new ParkingSpotResponseDto(e))
				.collect(Collectors.toList());
    }

    /**
     * <p>
     * gets the unavailable parking spots for non-monthly users
     * </p>
     * 
     * @return List of the unavailable parking spots for non-monthly users
     */
    @GetMapping("/parkingSpot/unavailable/nonmonthly")
    public Iterable<ParkingSpotResponseDto> getUnavailableParkingSpotsNonMonthly() {
        return StreamSupport.stream(parkingSpotService.getUnavailableParkingSpotsNonMonthly().spliterator(), false)
				.map(e -> new ParkingSpotResponseDto(e))
				.collect(Collectors.toList());
    }

}
