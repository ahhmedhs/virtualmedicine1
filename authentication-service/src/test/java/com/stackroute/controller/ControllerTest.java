package com.stackroute.controller;

import com.stackroute.model.Doctor;
import com.stackroute.model.Patient;
import com.stackroute.service.DoctorService;
import com.stackroute.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ControllerTest {

    @Mock
    DoctorService doctorService;

    @Mock
    PatientService patientService;

    @InjectMocks
    AuthenticationController controller;

    @Test
    public void addDoctorTest(){

        Doctor doctor = new Doctor("k@gmail.com","k@123");

        when(doctorService.addDoctor(doctor)).thenReturn(doctor);

        ResponseEntity<Doctor> res = controller.addDoctor(doctor);

        assertEquals(HttpStatus.CREATED,res.getStatusCode());

        assertEquals(doctor,res.getBody());


    }

    @Test
    public void addPatientTest(){

        Patient patient = new Patient("k@gmail.com","k@123");

        when(patientService.addPatient(patient)).thenReturn(patient);

        ResponseEntity<Patient> res = controller.addPatient(patient);

        assertEquals(HttpStatus.CREATED,res.getStatusCode());

        assertEquals(patient,res.getBody());


    }





}
