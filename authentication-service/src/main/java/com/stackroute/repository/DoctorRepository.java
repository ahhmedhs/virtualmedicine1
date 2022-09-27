package com.stackroute.repository;

import com.stackroute.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,String> {

    Doctor findByEmail(String email);

}
