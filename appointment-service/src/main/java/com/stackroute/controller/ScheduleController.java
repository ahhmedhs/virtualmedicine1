package com.stackroute.controller;

import com.stackroute.exception.PastDateException;
import com.stackroute.exception.ScheduleAlreadyExistsException;
import com.stackroute.exception.ScheduleIdNotNullException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Schedule;

import com.stackroute.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
/*
The Controller to map all the schedule api's.
 */
@RestController
@RequestMapping("/api/v1/schedules/")
@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    private ResponseEntity responseEntity;
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /*
    description : create new Schedule
    api endpoint : /schedule
    http request : POST
    request body : Schedule
    success response : Created:201
    failure response : Internal Server Error:500, Bad Request:400, CONFLICT:409
    */
    @ApiOperation("This api is used for creating Schedule.")
    @PostMapping("schedule")
    public ResponseEntity createSchedule(@RequestBody Schedule schedule)  {

        try {

            List<Schedule> savedSchedules=scheduleService.saveSchedule(schedule);
            responseEntity = new ResponseEntity(savedSchedules, HttpStatus.CREATED);

        }catch (ScheduleIdNotNullException exception){
            logger.error("Schedule Id should not be null :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.BAD_REQUEST);
        }catch(PastDateException exception){
            logger.error("Schedule can not be created with past dates :", exception);
            responseEntity = new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }catch(ScheduleAlreadyExistsException exception){
            logger.error("Schedule already exist with these details :", exception);
            responseEntity = new ResponseEntity(exception, HttpStatus.CONFLICT);
        }catch (NullPointerException exception){
            logger.error("Please provide schedule details", exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
     description : update a Schedule
     api endpoint : /schedule
     http request : PUT
     request body : Schedule
     success response : OK:200
     failure response : Internal Server Error:500, Bad Request:400, Not Found:404
     */
    @ApiOperation("Api to Update the Schedule.")
    @PutMapping("schedule")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule)  {
        if(schedule!=null) {
            try {
                Schedule updatedSchedule = scheduleService.updateSchedule(schedule);
                responseEntity = new ResponseEntity(updatedSchedule, HttpStatus.OK);
            } catch (ScheduleNotFoundException exception) {
                logger.error("Schedule can not be found :", exception);
                responseEntity = new ResponseEntity(exception, HttpStatus.NOT_FOUND);
            } catch(PastDateException exception){
                logger.error("Schedule can not be updated with past dates :", exception);
                responseEntity = new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
            }catch (Exception exception) {
                logger.error("Exception :", exception);
                responseEntity = new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
        responseEntity=new ResponseEntity("Please provide schedule details",HttpStatus.BAD_REQUEST);
    }
        return responseEntity;
    }

    /*
     description : Api to fetch Schedule Details using scheduleId.
     api endpoint : /schedule/{scheduleId}
     http request : GET
     Path Variable : scheduleId
     success response : OK:200
     failure response : Internal Server Error:500, Not Found:404
     */
    @ApiOperation("Api to fetch Schedule Details using scheduleId.")
    @GetMapping("schedule/{scheduleId}")
    public ResponseEntity<Schedule> retrieveScheduleById(@PathVariable String scheduleId)  {
        try {
            Schedule schedule=scheduleService.getScheduleByScheduleId(scheduleId);
            responseEntity=new ResponseEntity(schedule,HttpStatus.OK);
        } catch (ScheduleNotFoundException exception) {
            logger.error("Schedule can not be found :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
     description : Api to fetch Schedule Details using doctor email.
     api endpoint : /schedule/{doctorEmail}
     http request : GET
     Path Variable : doctorEmail
     success response : OK:200
     failure response : Internal Server Error:500
     */
    @ApiOperation("Api to fetch all Schedules using doctorEmail.")
    @GetMapping("schedule/doctor/{doctorEmail}")
    public ResponseEntity<List<Schedule>> retrieveAllAvailableSchedulesByDoctorEmailId(@PathVariable String doctorEmail){
        try {
            List<Schedule> allSchedules = scheduleService.getAllAvailableScheduleByDoctorEmail(doctorEmail);
            responseEntity = new ResponseEntity<>(allSchedules, HttpStatus.OK);
        }catch(Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
     description : Api to fetch all Schedules Details using schedule email and schedule date.
     api endpoint : /schedule/{scheduleId}/{scheduleDate}
     http request : GET
     Path Variable : scheduleId,scheduleDate
     success response : OK:200
     failure response : Internal Server Error:500
     */
    @ApiOperation("Api to fetch all the Schedule using doctorEmail and scheduleDate.")
    @GetMapping("schedule/doctor/all/{doctorEmail}/{scheduleDate}")
    public ResponseEntity<List<Schedule>> retrieveAllSchedulesByDoctorEmailAndScheduleDate(@PathVariable String doctorEmail,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate scheduleDate){
        try {
            List<Schedule> allSchedules = scheduleService.getAllByDoctorEmailAndScheduleDate(doctorEmail,scheduleDate);
            responseEntity = new ResponseEntity<>(allSchedules, HttpStatus.OK);
        }catch(Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /*
     description : Api to fetch all the available Schedules Details using scheduleId.
     api endpoint : /schedule/{doctorEmail}/{scheduleDate}
     http request : GET
     Path Variable : scheduleId,scheduleDate
     success response : OK:200
     failure response : Internal Server Error:500
     */
    @ApiOperation("Api to fetch all the available Schedule using doctorEmail and scheduleDate.")
    @GetMapping("schedule/{doctorEmail}/{scheduleDate}")
    public ResponseEntity<List<Schedule>> retrieveAllAvailableSchedulesByDoctorEmailAndDate(@PathVariable String doctorEmail, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate scheduleDate){
        try{

            List<Schedule> allByDoctorEmailAndDate = scheduleService.findAllAvailableSchedulesByDoctorEmailAndDate(doctorEmail, scheduleDate);
            responseEntity=new ResponseEntity(allByDoctorEmailAndDate,HttpStatus.OK);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}


