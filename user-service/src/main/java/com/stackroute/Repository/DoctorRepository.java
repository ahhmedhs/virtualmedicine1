package com.stackroute.Repository;

import com.stackroute.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface DoctorRepository extends MongoRepository<Doctor,String> {
    Doctor findByEmail(String email);
     List<Doctor> findByCityAndSpecialization(String city ,String specialization);
@Query("{}")
  List<Doctor> findAllDoctors();
    Doctor save(Doctor doctor);
    @Query("{city:?0}")
     List<Doctor> findByCity(String city);
    @Query("{specialization:?0}")
     List<Doctor> findBySpecialization(String specialization);
}
