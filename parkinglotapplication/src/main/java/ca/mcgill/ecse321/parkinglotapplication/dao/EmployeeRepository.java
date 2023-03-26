package ca.mcgill.ecse321.parkinglotapplication.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

    //PRIMARY KEY
    Employee findEmployeeByAccountId(int accountId);

    //delete employee
    void deleteByAccountId(int accountId);
    
    //find all employees
    List<Employee> findAll();

    //find all emp with name
    List<Employee> findEmployeeByName(String name);

    //find emp with email (assume email unique)
    Employee findEmployeeByEmail(String email);
    
}   
