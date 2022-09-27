package com.stackroute.service;

import com.stackroute.exceptions.BadCredentialsException;
import com.stackroute.exceptions.EmailAlreadyExistsException;
import com.stackroute.exceptions.EmailNotFoundException;
import com.stackroute.exceptions.PasswordNotFoundException;
import com.stackroute.model.Doctor;
import com.stackroute.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SecurityTokenGeneratorImpl securityTokenGenerator;


    /* Method to add/register a new doctor*/

    public Doctor addDoctor(Doctor doctor){
        if(doctor.getEmail()==null)
            throw new EmailNotFoundException("Email field is empty");

        else if(doctorRepository.findByEmail(doctor.getEmail()) != null)
            throw new EmailAlreadyExistsException("Email already registered");


        else if(doctor.getPassword()==null)
            throw new PasswordNotFoundException("Password field is empty");

        else {
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
           return doctorRepository.save(doctor);
        }

    }

    /* Method to login for doctor*/

    public Map<String,String> doctorLogin(Doctor doctor){

        Doctor doc = doctorRepository.findByEmail(doctor.getEmail());

        if(doc == null){
            throw new EmailNotFoundException("User doesn't existed");

        }

        if(passwordEncoder.matches(doctor.getPassword(),doc.getPassword())){
                return securityTokenGenerator.generateToken(doctor);
        }
        else{
            throw new BadCredentialsException("Credentials are Invalid");
        }


    }

}
