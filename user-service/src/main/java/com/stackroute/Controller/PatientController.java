package com.stackroute.Controller;

import com.stackroute.Service.PatientService;
import com.stackroute.exception.PatientAlreadyExistsException;
import com.stackroute.exception.PatientNotFoundException;
import com.stackroute.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class PatientController {
    ResponseEntity responseEntity ;
    @Autowired
    private PatientService patientService;

        /* Create API endpoints as per the requirements given below
         */

        /*
        description : register patient
        api endpoint : /patient
        http request : POST
        request body : patient email
        success response : Created:201
        failure response : Internal Server Error:500
        */
    @PostMapping("/patient")
    public ResponseEntity<Patient> register(@RequestBody Patient patient){
        try {
            System.out.println("here"+ patient);
            Patient savePatient = patientService.savePatient(patient);
            responseEntity = new ResponseEntity<>(savePatient, HttpStatus.CREATED);
        }catch (PatientAlreadyExistsException exception){
            responseEntity=new ResponseEntity(exception,HttpStatus.CONFLICT);
        }

        return responseEntity;
    }
        /*
        description : update patient
        api endpoint : /patient
        http request : PUT
        request body : patient email
        success response : Created:201
        failure response : Internal Server Error:500
        */

    @PutMapping("/patient")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) throws PatientNotFoundException {
        try {
            Patient updatedPatient= patientService.updatePatient(patient);
            responseEntity=new ResponseEntity(updatedPatient,HttpStatus.OK);
        } catch (PatientNotFoundException e) {
            responseEntity=new ResponseEntity(e,HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
         /*
        description : get patient
        api endpoint : /patient/{patientEmail}
        http request : GET
        success response : Created:201
        failure response : Internal Server Error:500
        */


    @GetMapping("patient/{patientEmail}")
    public ResponseEntity<Patient> getPatient(@PathVariable String patientEmail) {
        responseEntity = new ResponseEntity<>(patientService.findByEmailId(patientEmail), HttpStatus.OK);
        return responseEntity;
    }
}
