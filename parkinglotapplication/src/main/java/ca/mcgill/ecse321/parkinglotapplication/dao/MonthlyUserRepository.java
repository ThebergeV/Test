package ca.mcgill.ecse321.parkinglotapplication.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.MonthlyUser;

public interface MonthlyUserRepository extends CrudRepository<MonthlyUser, Integer>{

    //PRIMARY KEY
    MonthlyUser findMonthlyUserByAccountId(int accountId);

    //delete monthlyUser
    void deleteByAccountId(int accountId);

    //delete all emp
    void deleteAll();

    //find all monthlyUsers
    List<MonthlyUser> findAll();

    //find all emp with name
    List<MonthlyUser> findMonthlyUserByName(String name);

    //find emp with email (assume email unique)
    MonthlyUser findMonthlyUserByEmail(String email);

    MonthlyUser findMonthlyUserByLicensePlateNumber(String licensePlateNumber);

    
}   
