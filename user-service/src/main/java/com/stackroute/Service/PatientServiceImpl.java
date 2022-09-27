package com.stackroute.Service;

import com.stackroute.Repository.PatientRepository;
import com.stackroute.config.Producer;
import com.stackroute.exception.PatientAlreadyExistsException;
import com.stackroute.exception.PatientNotFoundException;
import com.stackroute.model.Patient;
import com.stackroute.rabbitMq.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public  class PatientServiceImpl implements PatientService {
    @Autowired
    Producer producer;
    @Autowired
    public PatientRepository patientRepository;

    @Override
    public Patient savePatient(Patient patient) {

        if (patientRepository.findByEmail(patient.getEmail())!=null) {
            throw new PatientAlreadyExistsException("Patient Already Exists with this Emailid");
        }
        else {
            PatientDTO patientDTO=new PatientDTO();
            patientDTO.setEmail(patient.getEmail());
            patientDTO.setPassword(patient.getPassword());
            producer.sendMessageToRabbitMq(patientDTO);
            patient.setPassword("");
            return patientRepository.save(patient);
        }
    }
      @Override
        public Patient updatePatient(Patient patient) {
            if (patient.getEmail()==null){
                throw new PatientNotFoundException("Patient is Not Found");
            }
            return patientRepository.save(patient);
        }
        @Override
        public Patient findByEmailId(String patientEmail) {
        Patient patient=patientRepository.findByEmail(patientEmail);
            return patientRepository.save(patient);
        }


    }
