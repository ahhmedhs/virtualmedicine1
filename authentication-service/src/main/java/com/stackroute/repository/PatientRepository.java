package com.stackroute.repository;

import com.stackroute.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,String> {

    Patient findByEmail(String email);
}
