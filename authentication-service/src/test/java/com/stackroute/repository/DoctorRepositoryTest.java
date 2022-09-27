//package com.stackroute.repository;
//
//import com.stackroute.model.Doctor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class DoctorRepositoryTest {
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//
//    @Test
//    void findByEmailAndTestForEmail() {
//        Doctor doctor = new Doctor("a@gmail.com","123");
//        doctorRepository.save(doctor);
//
//        Doctor doc = doctorRepository.findByEmail(doctor.getEmail());
//
//        assertEquals(doc.getEmail(),doctor.getEmail());
//
//
//    }
//    @Test
//    void findByEmailAndTestForPassword() {
//        Doctor doctor = new Doctor("a@gmail.com","123");
//        doctorRepository.save(doctor);
//
//        Doctor doc = doctorRepository.findByEmail(doctor.getEmail());
//
//
//        assertEquals(doc.getPassword(),doctor.getPassword());
//
//
//    }
//}