package com.stackroute.service;

import com.stackroute.exception.PastDateException;
import com.stackroute.exception.ScheduleAlreadyExistsException;
import com.stackroute.exception.ScheduleIdNotNullException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Appointment;
import com.stackroute.model.AppointmentStatus;
import com.stackroute.model.Schedule;
import com.stackroute.model.ScheduleStatus;
import com.stackroute.repository.ScheduleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private IdGeneratorService idGeneratorService;

    private Optional<Schedule> retrievedSchedule;


    /*
        This method will fetch all available schedules by doctorEmail and returns List of Schedules.
     */
    @Override
    public List<Schedule> getAllAvailableScheduleByDoctorEmail(String doctorEmail) {

        return scheduleRepository.findAllAvailableScheduleByDoctorEmail(doctorEmail);
    }

    /*
        This method will fetch a single schedule by scheduleId and returns Schedule Object.
     */
    @Override
    public Schedule getScheduleByScheduleId(String scheduleId) throws ScheduleNotFoundException {
        retrievedSchedule=scheduleRepository.findById(scheduleId);

        if(retrievedSchedule.isPresent()){
            return retrievedSchedule.get();
        }
        else{
            throw new ScheduleNotFoundException("Schedule is not found.");
        }
    }

    /*
        This method will fetch all the available schedules by doctorEmail and scheduleDate and returns List of Schedule.
     */
    @Override
    public List<Schedule> findAllAvailableSchedulesByDoctorEmailAndDate(String doctorEmail, LocalDate scheduleDate) {
        return scheduleRepository.findAllByDoctorEmailAndDate(doctorEmail,scheduleDate);
    }

    /*
        This method will fetch all the schedules by doctorEmail and scheduleDate and returns List of Schedule.
     */
    @Override
    public List<Schedule> getAllByDoctorEmailAndScheduleDate(String doctorEmail,LocalDate scheduleDate) {
        return scheduleRepository.findAllByDoctorEmailAndScheduleDate(doctorEmail,scheduleDate);
    }

    /*
        This method will  call splitAndSaveSchedules method to save the schedules and return the schedule object.
     */
    @Override
    public List<Schedule> saveSchedule(Schedule schedule) throws ScheduleIdNotNullException, PastDateException, ScheduleAlreadyExistsException {
        if(schedule.getScheduleId()==null) {
            if(schedule.getScheduleDate().isEqual(LocalDate.now()) | schedule.getScheduleDate().isAfter(LocalDate.now())) {

               return splitAndSaveSchedules(schedule);
            }
            else{
                throw new PastDateException("Scheduled can not be created with past dates");
            }
        }
        throw new ScheduleIdNotNullException("Schedule Id should be null.");
    }


    /*
       This method will update Schedules and return the updated schedule object.
     */
    @Override
    public Schedule updateSchedule(Schedule schedule) throws ScheduleNotFoundException, PastDateException {

        if(schedule.getScheduleDate().isEqual(LocalDate.now()) | schedule.getScheduleDate().isAfter(LocalDate.now())) {
            retrievedSchedule = scheduleRepository.findById(schedule.getScheduleId());
            if (retrievedSchedule.isPresent()) {

                Schedule updatedSchedule = scheduleRepository.save(schedule);
                if(updatedSchedule.getScheduleStatus().equals(ScheduleStatus.NOT_AVAILABLE)){

                    Appointment appointment=appointmentService.getAppointmentByScheduleId(updatedSchedule.getScheduleId());
                   if(appointment!=null) {
                       appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                          appointmentService.updateAppointmentProxy(appointment);
                   }
            }
                return updatedSchedule;
            } else {
                throw new ScheduleNotFoundException("Schedule is not found");
            }
        }
        else {
            throw new PastDateException("Schedule can not be updated with past dates");
        }
    }

/*
    public Schedule cancelSchedule(String scheduleId) throws PastDateException, AppointmentNotFoundException, ScheduleNotFoundException {
        Schedule schedule=getScheduleByScheduleId(scheduleId);
        if(schedule!=null){
           schedule.setScheduleStatus(ScheduleStatus.NOT_AVAILABLE);
           Appointment appointment=appointmentService.getAppointmentByScheduleId(schedule.getScheduleId());
           appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
           appointmentService.updateAppointment(appointment);
           return schedule;
        }else{
            throw new ScheduleNotFoundException("Schedule not found.");
        }
    }
 */


/*
    This method will split all the schedules with a difference of 30 minutes.
*/
    public List<Schedule> splitAndSaveSchedules(Schedule schedule) throws ScheduleAlreadyExistsException {
            List<Schedule> createdSchedules=new ArrayList<>();


            LocalTime startTime=schedule.getStartTime();
            LocalTime endTime=schedule.getEndTime();

        while(startTime.isAfter(endTime)){
            schedule.setStartTime(startTime);
            startTime=startTime.plus(30, ChronoUnit.MINUTES);
            schedule.setEndTime(startTime);
            createdSchedules.add(saveAfterSplit(schedule));


        }
        while(startTime.isBefore(endTime.minus(29,ChronoUnit.MINUTES)) ){
            schedule.setStartTime(startTime);
            startTime=startTime.plus(30, ChronoUnit.MINUTES);
            schedule.setEndTime(startTime);
            createdSchedules.add(saveAfterSplit(schedule));


        }
        return createdSchedules;
    }

/*
This method will save all the schedules with a difference of 30 minutes.
 */
public Schedule saveAfterSplit(Schedule schedule) throws ScheduleAlreadyExistsException {

            Schedule retrievedSchedule=scheduleRepository.findByDoctorEmailAndScheduleDateAndStartTimeAndEndTime(
            schedule.getDoctorEmail(),schedule.getScheduleDate(),schedule.getStartTime(),schedule.getEndTime());
        if(retrievedSchedule==null) {
            schedule.setScheduleId(idGeneratorService.generateId());
            schedule.setScheduleStatus(ScheduleStatus.AVAILABLE);
            return scheduleRepository.save(schedule);
        }
        else {
            throw new ScheduleAlreadyExistsException("Schedule already exists");
        }
    }
}
