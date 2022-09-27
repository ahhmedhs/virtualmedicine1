//package com.stackroute.repository;
//
//import com.stackroute.model.Doctor;
//import com.stackroute.model.Patient;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class PatientRepositoryTest {
//
//    @Autowired
//    PatientRepository patientRepository;
//
//
//    @Test
//    void findByEmailAndTestForEmail() {
//       Patient patient = new Patient("b@gmail.com","123");
//        patientRepository.save(patient);
//
//        Patient pat = patientRepository.findByEmail(patient.getEmail());
//
//        assertEquals(pat.getEmail(),patient.getEmail());
//
//
//
//    }
//    @Test
//    void findByEmailAndTestForPassword() {
//        Patient patient = new Patient("b@gmail.com","123");
//        patientRepository.save(patient);
//
//        Patient pat = patientRepository.findByEmail(patient.getEmail());
//
//
//        assertEquals(pat.getPassword(),patient.getPassword());
//
//
//    }
//}