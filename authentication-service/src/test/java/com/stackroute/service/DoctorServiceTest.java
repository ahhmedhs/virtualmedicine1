package com.stackroute.service;

import com.stackroute.model.Doctor;
import com.stackroute.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorServiceTest {

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    DoctorServiceImpl doctorService;

    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    void addDoctor() {


        Doctor doctor = new Doctor("bj@gmail.com","123");


        when(doctorRepository.save(doctor)).thenReturn(doctor);

        when(passwordEncoder.encode(doctor.getPassword())).thenReturn("123");

        assertEquals(doctor,doctorService.addDoctor(doctor));

    }


}