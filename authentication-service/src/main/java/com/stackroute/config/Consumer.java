package com.stackroute.config;

import com.stackroute.exceptions.EmailAlreadyExistsException;
import com.stackroute.model.Doctor;
import com.stackroute.model.Patient;
import com.stackroute.rabbitMQ.DoctorDTO;
import com.stackroute.rabbitMQ.PatientDTO;
import com.stackroute.service.DoctorService;
import com.stackroute.service.PatientService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @RabbitListener(queues="doctor_queue")
    public void getDoctorDtoFromRabbitMq(DoctorDTO doctorDto) throws EmailAlreadyExistsException {
        System.out.println(doctorDto.toString());
        Doctor doctor=new Doctor();
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPassword(doctorDto.getPassword());
        doctorService.addDoctor(doctor);
    }

    @RabbitListener(queues="patient_queue")
    public void getPatientDtoFromRabbitMq(PatientDTO patientDto) throws EmailAlreadyExistsException {
        System.out.println(patientDto.toString());
        Patient patient=new Patient();
        patient.setEmail(patientDto.getEmail());
        patient.setPassword(patientDto.getPassword());
        patientService.addPatient(patient);
    }

}