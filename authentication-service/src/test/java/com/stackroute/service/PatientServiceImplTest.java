package com.stackroute.service;

import com.stackroute.exceptions.BadCredentialsException;
import com.stackroute.exceptions.EmailNotFoundException;
import com.stackroute.model.Doctor;
import com.stackroute.model.Patient;
import com.stackroute.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceImplTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    SecurityTokenGeneratorImpl securityTokenGenerator;

    @InjectMocks
    PatientServiceImpl patientService;

    @Test
    void doctorLoginFailureTest() {

        when(patientRepository.findByEmail("k@gmail.com")).thenReturn(null);
        assertThrows(EmailNotFoundException.class,()->patientService.patientLogin(new Patient("k@gmail.com","k@123")));

    }
    @Test
    void doctorLoginWrongCredentialsTest(){

        Patient patient = new Patient("k@gmail.com","k@123");

        Patient patient2 = new Patient("k@gmail.com","123");



        when(patientRepository.findByEmail("k@gmail.com")).thenReturn(patient2);

        when(passwordEncoder.matches(patient.getPassword(),patient2.getPassword())).thenReturn(false);

        assertThrows(BadCredentialsException.class,()->patientService.patientLogin(patient));

    }

    @Test
    void doctorLoginSuccessTest(){

        Patient patient = new Patient("k@gmail.com","k@123");

        Map<String,String> map = new HashMap<>();
        map.put("token","J@123");
        map.put("message","login-success");

        when(patientRepository.findByEmail("k@gmail.com")).thenReturn(patient);

        when(securityTokenGenerator.generateToken(patient)).thenReturn(map);

        when(passwordEncoder.matches(patient.getPassword(),patient.getPassword())).thenReturn(true);


        assertEquals(map,patientService.patientLogin(patient));


    }

}