package com.stackroute.Service;

import com.stackroute.model.Doctor;

import java.util.List;

public interface DoctorService {
     Doctor saveDoctor (Doctor doctor);
     Doctor updateDoctor (Doctor doctor);

      Doctor findByEmailId(String doctorEmail) ;

      List<Doctor> findByCityAndSpecialization(String city, String specialization) ;

    List<Doctor> findDoctors();
}
