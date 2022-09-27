package com.stackroute.service;


import com.stackroute.exception.AppointmentAlreadyExistsException;
import com.stackroute.exception.AppointmentIdNotNullException;
import com.stackroute.exception.AppointmentNotFoundException;
import com.stackroute.exception.PastDateException;
import com.stackroute.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {

    /*This method will return an Appointment based on Appointment Id */
     Appointment getAppointmentByAppointmentId(String appointmentId) throws AppointmentNotFoundException;

    /*This method will return all the Appointments for a doctor based on his email id  */
     List<Appointment> getAllAppointmentsByDoctorEmail(String doctorEmail);

    /*This method will return all Appointments for patient based on emial id */
     List<Appointment> getAllAppointmentsByPatientEmail(String patientEmail);

    /*This method will retrieve all the appointments by scheuduleId*/
     Appointment getAppointmentByScheduleId(String scheduleId);

    /*This method will save the Appointment Details */
     Appointment saveAppointment(Appointment appointment) throws AppointmentIdNotNullException, PastDateException, AppointmentAlreadyExistsException;

    /*This method will update the Appointment Details */
     Appointment updateAppointment(Appointment appointment) throws AppointmentNotFoundException, PastDateException;

     Appointment updateAppointmentProxy(Appointment appointment);


}
