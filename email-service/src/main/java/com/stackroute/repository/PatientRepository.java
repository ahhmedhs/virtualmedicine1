package com.stackroute.repository;

import com.stackroute.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient,String> {
}
