package com.stackroute.repository;

import com.stackroute.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ScheduleRepository extends MongoRepository<Schedule,String> {


    /*This method will retrieve all schedules by doctor email*/
    @Query("{doctorEmail :?0 }")
     List<Schedule> findAllAvailableScheduleByDoctorEmail(String doctorEmail);

    @Query("{doctorEmail :?0 , scheduleDate :?1, scheduleStatus:AVAILABLE}")
     List<Schedule> findAllByDoctorEmailAndDate(String doctorEmail,LocalDate scheduleDate);

     Schedule findByDoctorEmailAndScheduleDateAndStartTimeAndEndTime(String doctorEmail, LocalDate scheduleDate, LocalTime
                                                                           startTime, LocalTime endTime);

     List<Schedule> findAllByDoctorEmailAndScheduleDate(String doctorEmail,LocalDate scheduleDate);

  }
