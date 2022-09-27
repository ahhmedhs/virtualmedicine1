package com.stackroute.service;

import com.stackroute.model.Patient;

import java.util.Map;

public interface PatientService {

     Patient addPatient(Patient patient);

     Map<String,String > patientLogin(Patient patient);

}
