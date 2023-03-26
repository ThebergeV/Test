package ca.mcgill.ecse321.parkinglotapplication.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.NonMonthlyUser;

public interface NonMonthlyUserRepository extends CrudRepository<NonMonthlyUser, Integer>{

    //PRIMARY KEY
    NonMonthlyUser findNonMonthlyUserByAccountId(int accountId);

    //delete nonMonthlyUser
    void deleteByAccountId(int accountId);

    //delete all emp
    void deleteAll();

    //find all nonMonthlyUsers
    List<NonMonthlyUser> findAll();

    //find all emp with name
    List<NonMonthlyUser> findNonMonthlyUserByName(String name);

    //find emp with email (assume email unique)
    NonMonthlyUser findNonMonthlyUserByEmail(String email);

    NonMonthlyUser findNonMonthlyUserByLicensePlateNumber(String licensePlateNumber);

    
}   
