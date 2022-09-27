package com.stackroute.service;

import com.stackroute.exceptions.BadCredentialsException;
import com.stackroute.exceptions.EmailNotFoundException;
import com.stackroute.model.Doctor;
import com.stackroute.repository.DoctorRepository;
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
class DoctorServiceImplTest {

    @Mock
    SecurityTokenGeneratorImpl securityTokenGeneratorImpl;
    @Mock
    DoctorRepository doctorRepository;

    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    DoctorServiceImpl doctorService;




    @Test
    void doctorLoginFailureTest() {

        when(doctorRepository.findByEmail("k@gmail.com")).thenReturn(null);
        assertThrows(EmailNotFoundException.class,()->doctorService.doctorLogin(new Doctor("k@gmail.com","k@123")));

    }
    @Test
    void doctorLoginWrongCredentialsTest(){

        Doctor doctor = new Doctor("k@gmail.com","k@123");

        Doctor doctor2 = new Doctor("k@gmail.com", "1234");

        when(doctorRepository.findByEmail("k@gmail.com")).thenReturn(doctor2);

        when(passwordEncoder.matches(doctor.getPassword(),doctor2.getPassword())).thenReturn(false);

        assertThrows(BadCredentialsException.class,()->doctorService.doctorLogin(doctor));

    }

    @Test
    void doctorLoginSuccessTest(){

        Doctor doctor = new Doctor("k@gmail.com","k@123");

       when(doctorRepository.findByEmail("k@gmail.com")).thenReturn(doctor);

        Map<String,String> map = new HashMap<>();
        map.put("token","Jwt123");
        map.put("message","login-success");

        when(securityTokenGeneratorImpl.generateToken(doctor)).thenReturn(map);

        when(passwordEncoder.matches(doctor.getPassword(),doctor.getPassword())).thenReturn(true);


        assertEquals(map,doctorService.doctorLogin(doctor));


    }

}