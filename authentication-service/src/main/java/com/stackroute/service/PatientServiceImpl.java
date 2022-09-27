package com.stackroute.service;

import com.stackroute.exceptions.BadCredentialsException;
import com.stackroute.exceptions.EmailAlreadyExistsException;
import com.stackroute.exceptions.EmailNotFoundException;
import com.stackroute.exceptions.PasswordNotFoundException;
import com.stackroute.model.Patient;
import com.stackroute.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SecurityTokenGeneratorImpl securityTokenGenerator;

    /*Method for add/register a new patient*/

    public Patient addPatient(Patient patient){
        if(patient.getEmail()==null)
            throw new EmailNotFoundException("Email field is empty");

        else if(patientRepository.findByEmail(patient.getEmail()) != null)
            throw new EmailAlreadyExistsException("Email already registered");


        else if(patient.getPassword()==null)
            throw new PasswordNotFoundException("Password field is empty");

        else {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            return patientRepository.save(patient);
        }

    }

    /*Method to login for patient*/

    public Map<String,String> patientLogin(Patient patient){

        Patient pat = patientRepository.findByEmail(patient.getEmail());

        if(pat == null){
            throw new EmailNotFoundException("User doesn't existed");

        }

        if(passwordEncoder.matches(patient.getPassword(),pat.getPassword())){
            return securityTokenGenerator.generateToken(patient);
        }
        else{
            throw new BadCredentialsException("Credentials are Invalid");
        }


    }

}
