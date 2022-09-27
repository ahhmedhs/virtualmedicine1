package com.stackroute.controller;

import com.stackroute.exceptions.BadCredentialsException;
import com.stackroute.exceptions.EmailAlreadyExistsException;
import com.stackroute.exceptions.EmailNotFoundException;
import com.stackroute.exceptions.PasswordNotFoundException;
import com.stackroute.model.Doctor;
import com.stackroute.model.Patient;
import com.stackroute.service.DoctorService;
import com.stackroute.service.PatientService;
import com.stackroute.service.SecurityTokenGeneratorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    SecurityTokenGeneratorImpl securityTokenGenerator;

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;
    private ResponseEntity responseEntity;
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /* Create API endpoints as per the requirements given below
    */

    /*
    description : create new doctor
    api endpoint : /doctor
    http request : POST
    request body : doctor email & password
    success response : Created:201
    failure response : Internal Server Error:500
    */
    @PostMapping("/doctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor){
        logger.debug("Request {}",doctor);
        try {
            Doctor saveDoctor = doctorService.addDoctor(doctor);
            responseEntity = new ResponseEntity<>(saveDoctor, HttpStatus.CREATED);
        }catch (EmailAlreadyExistsException exception){
            logger.error("Email already exists", exception);
            responseEntity = new ResponseEntity<>(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (EmailNotFoundException exception){
            logger.error("Email not found", exception);
            responseEntity = new ResponseEntity<>(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (PasswordNotFoundException exception){
            logger.error("Password not found", exception);
            responseEntity = new ResponseEntity<>(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.debug("Response {} ",responseEntity);
        return responseEntity;
    }


    /*
    description : create new patient
    api endpoint : /patient
    http request : POST
    request body : patient email & password
    success response : Created:201
    failure response : Internal Server Error:500
    */
    @PostMapping("/patient")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient){
        logger.debug("Request {}",patient);
        try {
            Patient savePatient = patientService.addPatient(patient);
            responseEntity = new ResponseEntity<>(savePatient, HttpStatus.CREATED);
        }catch (EmailAlreadyExistsException exception){
            logger.error("Email already exists", exception);
            responseEntity = new ResponseEntity<>(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (EmailNotFoundException exception){
            logger.error("Email not found", exception);
            responseEntity = new ResponseEntity<>(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (PasswordNotFoundException exception){
            logger.error("Password not found", exception);
            responseEntity = new ResponseEntity<>(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.debug("Response {} ",responseEntity);
        return responseEntity;

    }

    /*
    description :  login for doctor
    api endpoint : /doctor/login
    http request : POST
    request body : doctor email & password
    success response : Accepted:202
    failure response : Internal Server Error:500
    */
    @PostMapping("doctor/login")
    public ResponseEntity authenticateDoctor(@RequestBody Doctor doctor){
        logger.debug("Request {} ",doctor);
        try {
            responseEntity=new ResponseEntity(doctorService.doctorLogin(doctor), HttpStatus.ACCEPTED);
        }catch (BadCredentialsException exception){
            logger.error("Bad Credentials",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (EmailNotFoundException exception){
            logger.error("Email not found",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("Response {} ",responseEntity);
        return responseEntity;
    }


      /*
    description :  login for patient
    api endpoint : /patient/login
    http request : POST
    request body : patient email & password
    success response : Accepted:202
    failure response : Internal Server Error:500
    */
    @PostMapping("patient/login")
    public ResponseEntity authenticateDoctor(@RequestBody Patient patient){
        logger.debug("Request {} ",patient);

        try {
            responseEntity=new ResponseEntity(patientService.patientLogin(patient), HttpStatus.ACCEPTED);
        }catch (BadCredentialsException exception){
            logger.error("Bad Credentials",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (EmailNotFoundException exception){
            logger.error("Email not found",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.debug("Response {} ",responseEntity);
        return responseEntity;
    }
}
