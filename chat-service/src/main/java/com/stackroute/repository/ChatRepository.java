package com.stackroute.repository;

import com.stackroute.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends MongoRepository<Chat,String> {

     Chat findByAppointmentId(String appointmentId);

     Chat findByDoctorEmail(String doctorEmail);

     Chat findByPatientEmail(String patientEmail);

}
