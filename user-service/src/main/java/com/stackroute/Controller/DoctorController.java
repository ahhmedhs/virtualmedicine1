package com.stackroute.Controller;

import com.stackroute.Service.DoctorService;
import com.stackroute.exception.DoctorNotFoundException;
import com.stackroute.model.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
@CrossOrigin
public class DoctorController {
    ResponseEntity responseEntity;
    @Autowired
    private DoctorService doctorService;


        /* Create API endpoints as per the requirements given below
         */

        /*
        description : save doctor
        api endpoint : /doctor
        http request : POST
        request body : doctor email
        success response : Created:201
        failure response : Internal Server Error:500
        */

    @PostMapping("doctor")
    public ResponseEntity<Doctor> register(@RequestBody Doctor doctor) {

        try {
            Doctor saveDoctor = doctorService.saveDoctor(doctor);
            responseEntity = new ResponseEntity<>(saveDoctor, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
        /*
        description : update doctor
        api endpoint : /doctor
        http request : PUT
        request body : doctor email
        success response : Created:201
        failure response : Internal Server Error:500
        */
    @PutMapping("doctor")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) throws DoctorNotFoundException {
        try {
            Doctor updatedDoctor= doctorService.updateDoctor(doctor);
            responseEntity=new ResponseEntity(updatedDoctor,HttpStatus.OK);
        } catch (DoctorNotFoundException e) {
            throw new DoctorNotFoundException("Doctor is not found.");
        }
        return responseEntity;
    }
        /*
        description : get doctor
        api endpoint : /doctor/{doctorEmail}
        http request : GET
        success response : Created:201
        failure response : Internal Server Error:500
        */

    @GetMapping("doctor/{doctorEmail}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable String doctorEmail) {
        try {
            responseEntity = new ResponseEntity<>(doctorService.findByEmailId(doctorEmail), HttpStatus.OK);
        }catch (DoctorNotFoundException exception){
            responseEntity=new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        }
        return responseEntity;

    }
        /*
        description : get doctor
        api endpoint : /doctor/city/specialization
        http request : GET
        success response : Created:201
        failure response : Internal Server Error:500
        */


    @GetMapping("doctor/city/specialization")
    public ResponseEntity<List<Doctor>> getDoctorByCityAndSpecialization(@RequestParam String city, @RequestParam String specialization) {
        responseEntity = new ResponseEntity<>(doctorService.findByCityAndSpecialization(city, specialization), HttpStatus.OK);
        return responseEntity;
    }
    /*
       description : get alldoctor
       api endpoint : /doctor
       http request : GET
       success response : Created:201
       failure response : Internal Server Error:500
       */
    @GetMapping("doctor")
    public ResponseEntity<Doctor> getDoctors(){
        responseEntity=new ResponseEntity<>(doctorService.findDoctors(),HttpStatus.FOUND);
        return responseEntity;
    }



}
