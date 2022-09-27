package com.stackroute.service;


import com.stackroute.config.MQConfig;
import com.stackroute.model.AppointmentStatus;
import com.stackroute.model.Doctor;
import com.stackroute.model.MQMessage;
import com.stackroute.model.Patient;
import com.stackroute.repository.DoctorRepository;
import com.stackroute.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.print.Doc;

@Component
public class MailSenderServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    @RabbitListener(queues = MQConfig.APPOINTMENT_QUEUE)
    public void messageListener(MQMessage message){
        packageAndSend(message);
    }

    public void packageAndSend(MQMessage message){

        Doctor doctor = doctorRepository.findById(message.getDoctorEmail()).get();
        Patient patient = patientRepository.findById(message.getPatientEmail()).get();

        String doctorBody = "Hi "+doctor.getName()+", \n \n Your appointment is " + message.getAppointmentStatus() +"."+" \n\n Patient Name: "+patient.getName()+
                " \n Appointment Date : " + message.getAppointmentDate() + " \n\n Time : \n From :" + message.getStartTime()
                + "\n To :" + message.getEndTime();

        String patientBody = "Hi "+patient.getName()+", \n \n Your appointment is " + message.getAppointmentStatus()+message.getDoneBy()+"." +" \n\n Doctor Name: "+doctor.getName()+
                " \n Appointment Date : " + message.getAppointmentDate() + " \n \n Time : \n From :" + message.getStartTime()
                + "\n To :" + message.getEndTime();

        if(message.getDoneBy().equals("")) {
            sendMail(message.getDoctorEmail(), doctorBody, message.getAppointmentStatus());
            sendMail(message.getPatientEmail(), patientBody, message.getAppointmentStatus());
        }
        if (message.getDoneBy().equals(" by Doctor ")) {
            sendMail(message.getPatientEmail(), patientBody, message.getAppointmentStatus());
        }

    }


    public void sendMail(String to, String body, AppointmentStatus status){

        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(to);
        mail.setFrom("VirtualMedicine");
        mail.setText(body);
        mail.setSubject("Appointment "+status);
        try {
            mailSender.send(mail);
        }catch (Exception exception){
            logger.error("Exception occured while sending mail, Please provide a valid email id. :"+exception);
        }
    }


}
