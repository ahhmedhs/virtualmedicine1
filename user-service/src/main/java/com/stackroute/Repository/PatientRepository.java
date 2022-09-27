package com.stackroute.Repository;

import com.stackroute.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient,String> {
   Patient findByEmail(String email);

  Patient save(Patient patient);
}
