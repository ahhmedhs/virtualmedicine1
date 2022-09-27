package com.stackroute.service;

import com.stackroute.configuration.MQConfig;
import com.stackroute.exception.AppointmentIdNotNullException;
import com.stackroute.exception.PastDateException;
import com.stackroute.exception.AppointmentAlreadyExistsException;
import com.stackroute.exception.AppointmentNotFoundException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Appointment;
import com.stackroute.model.Schedule;
import com.stackroute.model.AppointmentStatus;
import com.stackroute.model.ScheduleStatus;
import com.stackroute.model.MQMessage;
import com.stackroute.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;
    private Optional<Appointment> retrievedAppointment;

    @Autowired
    private ScheduleService scheduleService;
    private Schedule schedule;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate template;

    /*
   This method will retrieve and return appointment using appointment id.
   */
    @Override
    public Appointment getAppointmentByAppointmentId(String appointmentId) throws AppointmentNotFoundException {
        retrievedAppointment= appointmentRepository.findById(appointmentId);

        if(retrievedAppointment.isPresent()){
            return retrievedAppointment.get();
        }
        else{
            throw new AppointmentNotFoundException("Appointment is not found.");
        }
    }

    /*
   This method will retrieve and return all the appointments using doctorEmail.
  */
    @Override
    public List<Appointment> getAllAppointmentsByDoctorEmail(String doctorEmail) {
        return appointmentRepository.findAllByDoctorEmail(doctorEmail);
    }

    /*
   This method will retrieve and return all the appointments using patient email.
   */
    @Override
    public List<Appointment> getAllAppointmentsByPatientEmail(String patientEmail) {

        return appointmentRepository.findAllByPatientEmail(patientEmail);
    }

    /*
   This method will retrieve and return appointment using schedule id.
   */
    @Override
    public Appointment getAppointmentByScheduleId(String scheduleId) {
        return appointmentRepository.findByScheduleId(scheduleId);
    }

    /*
    This method will save the appointment details in database.
    */
    @Override
    public Appointment saveAppointment(Appointment appointment) throws AppointmentIdNotNullException, PastDateException, AppointmentAlreadyExistsException {
        if(appointment.getAppointmentId()==null) {

            try {
                schedule = scheduleService.getScheduleByScheduleId(appointment.getScheduleId());
            } catch (ScheduleNotFoundException e) {
                logger.error("Schedule is not found.", e);
            }
            if (schedule.getScheduleStatus().equals(ScheduleStatus.AVAILABLE)) {

                appointment.setAppointmentId(idGeneratorService.generateId());
                appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
                appointment.setBookedOn(LocalDate.now());

                Appointment savedAppointment = appointmentRepository.save(appointment);
                schedule.setScheduleStatus(ScheduleStatus.BOOKED);

                try {
                    scheduleService.updateSchedule(schedule);
                } catch (ScheduleNotFoundException e) {
                    logger.error("Schedule not found",e);
                }
                    sendRabbitMQMessage(savedAppointment,"");


                return savedAppointment;
            } else {
                throw new AppointmentAlreadyExistsException("Appointment already exists.");
            }
        }

        throw new AppointmentIdNotNullException("Appointment Id should be null");
    }


    /*
   This method will update the appointment details.
   */
    @Override
    public Appointment updateAppointment(Appointment appointment) throws AppointmentNotFoundException, PastDateException {
        if(appointment.getAppointmentDate().isEqual(LocalDate.now()) |
                appointment.getAppointmentDate().isAfter(LocalDate.now())) {

            retrievedAppointment = appointmentRepository.findById(appointment.getAppointmentId());
            if (retrievedAppointment.isPresent()) {

                Appointment updatedAppointment = appointmentRepository.save(populateAppointmentToUpdate(retrievedAppointment,appointment));
                sendRabbitMQMessage(updatedAppointment,"");

                try {
                    schedule = scheduleService.getScheduleByScheduleId(appointment.getScheduleId());
                } catch (ScheduleNotFoundException e) {
                    logger.error("Schedule not Found exception:", e);
                }
                if (updatedAppointment.getAppointmentStatus().equals(AppointmentStatus.CANCELLED)) {
                    try {
                        schedule.setScheduleStatus(ScheduleStatus.AVAILABLE);
                        scheduleService.updateSchedule(schedule);
                    } catch (ScheduleNotFoundException e) {
                        logger.error("Schedule not Found exception:", e);
                    }
                    if (updatedAppointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED)) {
                        try {

                            schedule.setScheduleStatus(ScheduleStatus.NOT_AVAILABLE);
                            scheduleService.updateSchedule(schedule);
                        } catch (ScheduleNotFoundException e) {
                            logger.error("Schedule not Found exception:", e);
                        }
                    }

                }
                return updatedAppointment;
            }else {
                    throw new AppointmentNotFoundException("Appointment is not found");
                }
            } else {
                throw new PastDateException("Appointment can not be updated with past dates");
            }
        }

        public Appointment updateAppointmentProxy(Appointment appointment){
        Appointment cancelledAppointment= appointmentRepository.save(appointment);
        sendRabbitMQMessage(cancelledAppointment," by Doctor ");
        return cancelledAppointment;
        }

       /*
       This method will populate the MQMessage object to send it to RabbitMQ.
       */
        public MQMessage getMQMessage(Appointment appointment){
        MQMessage message=new MQMessage();
        message.setAppointmentDate(appointment.getAppointmentDate());
        message.setAppointmentStatus(appointment.getAppointmentStatus());
        message.setDoctorEmail(appointment.getDoctorEmail());
        message.setPatientEmail(appointment.getPatientEmail());
        message.setSpecialization(appointment.getSpecialization());
        message.setStartTime(appointment.getStartTime());
        message.setEndTime(appointment.getEndTime());
        return message;
        }

    /*
       This method will send the MQMessage object to  RabbitMQ.
    */
    private void sendRabbitMQMessage(Appointment savedAppointment,String doneBy) {
        MQMessage message=getMQMessage(savedAppointment);
        message.setDoneBy(doneBy);
        template.convertAndSend(MQConfig.APPOINTMENT_EXCHANGE,MQConfig.ROUTING_KEY,message);
    }

    private Appointment populateAppointmentToUpdate(Optional<Appointment> optAppointment,Appointment appointment){
        Appointment appointment1 = optAppointment.get();
        appointment1.setPatientEmail(appointment.getPatientEmail());
        appointment1.setDoctorEmail(appointment.getDoctorEmail());
        appointment1.setAppointmentDate(appointment.getAppointmentDate());
        appointment1.setScheduleId(appointment.getScheduleId());
        appointment1.setStartTime(appointment.getStartTime());
        appointment1.setEndTime(appointment.getEndTime());
        appointment1.setAppointmentStatus(appointment.getAppointmentStatus());
        appointment1.setPatientQuery(appointment.getPatientQuery());
        appointment1.setSpecialization(appointment.getSpecialization());
        appointment1.setBookedOn(appointment.getBookedOn());

        return appointment1;
    }


}
