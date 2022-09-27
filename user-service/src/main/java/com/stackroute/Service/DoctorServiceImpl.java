package com.stackroute.Service;

import com.stackroute.Repository.DoctorRepository;
import com.stackroute.config.Producer;
import com.stackroute.exception.DoctorAlreadyExistsException;
import com.stackroute.exception.DoctorNotFoundException;
import com.stackroute.model.Doctor;
import com.stackroute.rabbitMq.DoctorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    Producer producer;
     @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    private DoctorRepository doctorRepository;
    @Override
    public Doctor saveDoctor(Doctor doctor) {



        if (doctorRepository.findByEmail(doctor.getEmail()) != null) {

            throw new DoctorAlreadyExistsException("Doctor Already Exists with this Emailid");
        }
        else {
            DoctorDTO doctorDTO=new DoctorDTO();
            doctorDTO.setEmail(doctor.getEmail());
            doctorDTO.setPassword(doctor.getPassword());
            producer.sendMessageToRabbitMq(doctorDTO);
            doctor.setPassword("");
            return doctorRepository.save(doctor);
        }
    }
    @Override
    public Doctor updateDoctor(Doctor doctor) {
        if (doctor.getEmail()==null){
            throw new DoctorNotFoundException("Doctor is Not Found");
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findByEmailId(String doctorEmail) {
      Doctor doctor =doctorRepository.findByEmail(doctorEmail);
      if (doctor==null){
          throw new DoctorNotFoundException("Doctor is Not Found");
      }
      return doctor;
    }

    @Override
    public List<Doctor> findByCityAndSpecialization(String city, String specialization) {
        List<Doctor> doctorList=new ArrayList<>();
        if (specialization.equals("")) {
            doctorList = doctorRepository.findByCity(city);
        }else if (city.equals("")){
            doctorList=doctorRepository.findBySpecialization(specialization);
        }else {
            doctorList=doctorRepository.findByCityAndSpecialization(city, specialization);
        }

        return doctorList;
    }

    @Override
    public List<Doctor> findDoctors(){
        return doctorRepository.findAll();
    }

    }









