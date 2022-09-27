package com.stackroute.controller;


import com.stackroute.exception.AppointmentIdNotNullException;
import com.stackroute.exception.PastDateException;
import com.stackroute.exception.AppointmentAlreadyExistsException;
import com.stackroute.exception.AppointmentNotFoundException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Appointment;
import com.stackroute.model.AppointmentStatus;
import com.stackroute.model.Schedule;
import com.stackroute.service.AppointmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
/*
The Controller to map all the appointment api's.
 */
@RestController
@RequestMapping("/api/v1/appointments/")
@CrossOrigin
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;


    private ResponseEntity responseEntity;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    /*
   description : create new Appointment
   api endpoint : /appointment
   http request : POST
   request body : Appointment
   success response : Created:201
   failure response : Internal Server Error:500, Bad Request:400, Conflict:409
   */
    @PostMapping("appointment")
    @ApiOperation("Api to book Appointment")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) throws ScheduleNotFoundException, AppointmentAlreadyExistsException {

        if(appointment!=null) {
            try {
                Appointment bookedAppointment = appointmentService.saveAppointment(appointment);
                responseEntity = new ResponseEntity(bookedAppointment, HttpStatus.CREATED);
                //   ResponseEntity.status(HttpStatus.CREATED).body(bookedAppointment);
            } catch (AppointmentIdNotNullException exception) {
                logger.error("Appointment Id should be null :", exception);
                responseEntity = new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
            }catch(AppointmentAlreadyExistsException exception){
                logger.error("Appointment is already booked for this slot, please book another slot:", exception);
                responseEntity = new ResponseEntity(exception, HttpStatus.CONFLICT);
            }
            catch (Exception exception) {
                logger.error("Exception :", exception);
                responseEntity = new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            responseEntity=new ResponseEntity(NullPointerException.class,HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    /*
   description : update  Appointment
   api endpoint : /appointment
   http request : PUT
   request body : Appointment
   success response : OK:200
   failure response : Internal Server Error:500, Bad Request:400, Not Found:404
   */
    @PutMapping("appointment")
    @ApiOperation("Api to update Appointment")
    public ResponseEntity<Schedule> updateAppointment(@RequestBody Appointment appointment) throws AppointmentNotFoundException {
        if(appointment!=null) {
        try {
           Appointment updatedAppointment= appointmentService.updateAppointment(appointment);
           responseEntity=new ResponseEntity(updatedAppointment,HttpStatus.OK);
        } catch (AppointmentNotFoundException exception) {
           logger.error("Appointment can not be found :",exception);
           responseEntity=new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        } catch(PastDateException exception){
            logger.error("Appointment can not be updated with past dates :", exception);
            responseEntity = new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
        else {
            responseEntity=new ResponseEntity(NullPointerException.class,HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    /*
   description : api to retrieve appointment using patient email.
   api endpoint : /appointment/patient/{patientEmail}
   http request : GET
   Path Variable: patientEmail
   success response : OK:200
   failure response : Internal Server Error:500
   */
    @GetMapping("appointment/patient/{patientEmail}")
    @ApiOperation("api to retrieve appointment using patient email.")
    public ResponseEntity<List<Appointment>> retrieveAllAppointmentByPatientEmailId(@PathVariable String patientEmail){

        try {
            List<Appointment> allAppointments = appointmentService.getAllAppointmentsByPatientEmail(patientEmail);
            responseEntity=new ResponseEntity(allAppointments,HttpStatus.OK);
        }catch (Exception exception){
            logger.error("Exception:",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
   description : api to retrieve appointment using appointment id.
   api endpoint : /appointment/{appointmentId}
   http request : GET
   Path Variable: appointmentId
   success response : OK:200
   failure response : Internal Server Error:500, Not Found:404
   */
    @GetMapping("appointment/{appointmentId}")
    @ApiOperation("api to retrieve appointment using appointment id.")
    public ResponseEntity<Appointment> retrieveAppointmentById(@PathVariable String appointmentId) throws AppointmentNotFoundException {
        try {
            Appointment retrievedAppointment= appointmentService.getAppointmentByAppointmentId(appointmentId);
            responseEntity=new ResponseEntity(retrievedAppointment,HttpStatus.OK);
        } catch (AppointmentNotFoundException exception) {
            logger.error("Appointment can not be found :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
   description : api to retrieve appointment using doctor email.
   api endpoint : /appointment/doctor/{doctorEmail}
   http request : GET
   Path Variable: doctorEmail
   success response : OK:200
   failure response : Internal Server Error:500
   */
    @GetMapping("appointment/doctor/{doctorEmail}")
    @ApiOperation("api to retrieve appointment using doctor email.")
    public ResponseEntity<List<Appointment>> retrieveAllAppointmentsByDoctorEmail(@PathVariable String doctorEmail){

        try {
            List<Appointment>  allAppointments = appointmentService.getAllAppointmentsByDoctorEmail(doctorEmail);
            responseEntity=new ResponseEntity(allAppointments,HttpStatus.OK);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
   description : api to cancel appointment using appointment id.
   api endpoint : /appointment/cancel/{appointmentId}
   http request : DELETE
   Path Variable: appointmentId
   success response : OK:200
   failure response : Internal Server Error:500, Not Found:404
   */
    @DeleteMapping("appointment/cancel/{appointmentId}")
    @ApiOperation("api to cancel appointment using appointment id.")
    public ResponseEntity cancelAppointment(@PathVariable String appointmentId){
        try{
            Appointment appointment=  appointmentService.getAppointmentByAppointmentId(appointmentId);
            if(appointment==null)
                throw new AppointmentNotFoundException("Appointment not found");

          appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
          appointmentService.updateAppointment(appointment);
          responseEntity=new ResponseEntity(appointment,HttpStatus.OK);

        }catch (AppointmentNotFoundException exception){
            logger.error("Appointment can not be found :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /*
   description : api to retrieve appointment using schedule id.
   api endpoint : /appointment/schedule/{scheduleId}
   http request : GET
   Path Variable: scheduleId
   success response : OK:200
   failure response : Internal Server Error:500, Not Found
   */
    @GetMapping("appointment/schedule/{scheduleId}")
    @ApiOperation("api to retrieve appointment using schedule id.")
    public ResponseEntity retrieveAppointmentByScheduleId(@PathVariable String scheduleId){
        try {
            Appointment appointment = appointmentService.getAppointmentByScheduleId(scheduleId);
            if(appointment!=null)
            responseEntity = new ResponseEntity(appointment, HttpStatus.OK);
            else
                throw new AppointmentNotFoundException("Appointment not found");
        }catch(AppointmentNotFoundException exception){
            logger.error("Appointment not found",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            logger.error("Exception :",exception);
            responseEntity=new ResponseEntity(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
