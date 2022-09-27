package com.stackroute.service;
import com.stackroute.exception.PastDateException;
import com.stackroute.exception.ScheduleAlreadyExistsException;
import com.stackroute.exception.ScheduleIdNotNullException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ScheduleService {

    /*This method will return list of all the available schedules for a doctor based on his email id  */
     List<Schedule> getAllAvailableScheduleByDoctorEmail(String doctorEmail);

    /*This method will return a Schedule based on scheduleId */
     Schedule getScheduleByScheduleId(String scheduleId) throws ScheduleNotFoundException;

    /*This method will save the Schedule to database */
     List<Schedule> saveSchedule(Schedule schedule) throws ScheduleIdNotNullException, PastDateException, ScheduleAlreadyExistsException;

    /*This method will update the already created Schedule  */
     Schedule updateSchedule(Schedule schedule) throws ScheduleNotFoundException, PastDateException;

    /*This method will get all the already created Schedule by doctor email and date */
     List<Schedule> findAllAvailableSchedulesByDoctorEmailAndDate(String doctorEmail, LocalDate scheduleDate);

    /*This method will return list of all the  schedules for a doctor based on his email id  */
     List<Schedule> getAllByDoctorEmailAndScheduleDate(String doctorEmail,LocalDate scheduleDate);

     /*This method will split the schedules in the interval of 30 minutes */
    List<Schedule> splitAndSaveSchedules(Schedule schedule) throws ScheduleAlreadyExistsException;

    /*This method will save the splitted schedule in the database */
    Schedule saveAfterSplit(Schedule schedule) throws ScheduleAlreadyExistsException;

}
