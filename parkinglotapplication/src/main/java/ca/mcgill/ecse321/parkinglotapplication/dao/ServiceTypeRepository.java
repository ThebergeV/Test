package ca.mcgill.ecse321.parkinglotapplication.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.parkinglotapplication.model.ServiceType;

public interface ServiceTypeRepository extends CrudRepository<ServiceType, Integer>{

    //PRIMARY KEY
    ServiceType findServicetypeByServiceID(int serviceID);

}
