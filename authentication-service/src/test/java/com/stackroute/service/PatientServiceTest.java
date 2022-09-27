package com.stackroute.service;

import com.stackroute.model.Doctor;
import com.stackroute.model.Patient;
import com.stackroute.repository.DoctorRepository;
import com.stackroute.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest {


    @Mock
    PatientRepository patientRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PatientServiceImpl patientService;


    @Test
    void addPatient() {
        Patient patient = new Patient("patg@gmail.com","123");

        when(patientRepository.save(patient)).thenReturn(patient);

        when(passwordEncoder.encode(patient.getPassword())).thenReturn("123");

        assertEquals(patient,patientService.addPatient(patient));
    }





}