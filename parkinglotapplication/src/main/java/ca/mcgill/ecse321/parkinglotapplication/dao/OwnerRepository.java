package ca.mcgill.ecse321.parkinglotapplication.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Integer>{
    
    //PRIMARY KEY
    Owner findOwnerByAccountId(int accountId);
}
