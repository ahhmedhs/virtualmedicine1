package com.stackroute.repository;

import com.stackroute.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment,String> {

    /*This method will retrieve all the appointments by doctor email*/
    @Query("{doctorEmail :?0 , appointmentStatus:BOOKED}")
     List<Appointment> findAllByDoctorEmail(String doctorEmail);

    /*This method will retrieve all the appointments by patient email*/
    @Query("{patientEmail :?0}")
     List<Appointment> findAllByPatientEmail(String patientEmail);

    /*This method will retrieve all the appointments by scheduleId*/
    @Query("{scheduleId :?0 , appointmentStatus: BOOKED}")
     Appointment findByScheduleId(String scheduleId);
}
