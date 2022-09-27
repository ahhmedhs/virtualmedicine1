package com.stackroute.Service;


import com.stackroute.model.Patient;

public interface PatientService {

     Patient savePatient (Patient patient);
     Patient updatePatient(Patient patient);
     Patient findByEmailId(String patientEmail);
}
