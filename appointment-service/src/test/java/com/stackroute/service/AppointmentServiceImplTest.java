package com.stackroute.service;

import com.stackroute.exception.*;
import com.stackroute.model.Appointment;
import com.stackroute.model.AppointmentStatus;
import com.stackroute.model.Schedule;
import com.stackroute.model.ScheduleStatus;
import com.stackroute.repository.AppointmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private IdGeneratorService idGeneratorService;


    @Mock
    private ScheduleService scheduleService;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    private Appointment appointment;

    private List<Appointment> appointmentList;
    private Schedule schedule;

    @BeforeEach
    public void setUp(){
        appointment=new Appointment("shoeb.khan@gmail.com","saqib.khan@gmail.com", LocalDate.now(),"123", LocalTime.now(),LocalTime.now(), AppointmentStatus.BOOKED,
                "Mental Patient","MS",LocalDate.now());

        appointmentList=new ArrayList<>();
        appointmentList.add(appointment);

         schedule=new Schedule();

    }

    @Test
    public void GetAppointmentByAppointmentId_Test() throws AppointmentNotFoundException {
        when(appointmentRepository.findById("12345")).thenReturn(Optional.of(appointment));

        assertEquals(appointment,appointmentService.getAppointmentByAppointmentId("12345"));
    }

    @Test
    public void GetAppointmentByAppointmentId_NotFound_Test() throws AppointmentNotFoundException {
        when(appointmentRepository.findById("12345")).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class,()->appointmentService.getAppointmentByAppointmentId("12345"));
    }

    @Test
    public void getAllAppointmentsByDoctorEmail_Test(){

        when(appointmentRepository.findAllByDoctorEmail("saqib.khan@gmail.com")).thenReturn(appointmentList);

        assertEquals(appointmentList,appointmentService.getAllAppointmentsByDoctorEmail("saqib.khan@gmail.com"));

    }

    @Test
    public void getAllAppointmentsByDoctorEmail_NotFound_Test(){

        when(appointmentRepository.findAllByDoctorEmail("arvind.kumar@gmail.com")).thenReturn(Collections.EMPTY_LIST);

        assertEquals(Collections.EMPTY_LIST,appointmentService.getAllAppointmentsByDoctorEmail("arvind.kumar@gmail.com"));

    }

    @Test
    public void getAllAppointmentsByPatientEmail_Test(){
        when(appointmentRepository.findAllByPatientEmail("shoeb.khan@gmail.com")).thenReturn(appointmentList);

        assertEquals(appointmentList,appointmentService.getAllAppointmentsByPatientEmail("shoeb.khan@gmail.com"));
    }

    @Test
    public void getAllAppointmentsByPatientEmail_NotFound_Test(){
        when(appointmentRepository.findAllByPatientEmail("rehan.khan@gmail.com")).thenReturn(Collections.EMPTY_LIST);

        assertEquals(Collections.EMPTY_LIST,appointmentService.getAllAppointmentsByPatientEmail("rehan.khan@gmail.com"));
    }

    @Test
    public void getAppointmentByScheduleId_Test(){
        when(appointmentRepository.findByScheduleId("123")).thenReturn(appointment);
        assertEquals(appointment,appointmentService.getAppointmentByScheduleId("123"));
    }

    @Test
    public void getAppointmentByScheduleId_NotFound_Test(){
        when(appointmentRepository.findByScheduleId("124")).thenReturn(null);
        assertNull(appointmentService.getAppointmentByScheduleId("124"));
    }

    @Test
    public void saveAppointmentWithIdNotNull_Test(){
        Appointment appointment1=new Appointment();
        appointment1.setAppointmentId("12361");
        assertThrows(AppointmentIdNotNullException.class,()->appointmentService.saveAppointment(appointment1));
    }

        /*
    @Test
    public void saveAppointment_Test() throws ScheduleNotFoundException, PastDateException, AppointmentIdNotNullException, AppointmentAlreadyExistsException {
        schedule.setScheduleStatus(ScheduleStatus.AVAILABLE);

        when(scheduleService.getScheduleByScheduleId(appointment.getScheduleId())).thenReturn(schedule);
        when(idGeneratorService.generateId()).thenReturn("12345");
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(scheduleService.updateSchedule(schedule)).thenReturn(schedule);

        assertEquals(appointment,appointmentService.saveAppointment(appointment));
    }

         */

    @Test
    public void saveAppointment_AlreadyExists_Test() throws ScheduleNotFoundException {
        schedule.setScheduleStatus(ScheduleStatus.BOOKED);

        when(scheduleService.getScheduleByScheduleId(appointment.getScheduleId())).thenReturn(schedule);

        assertThrows(AppointmentAlreadyExistsException.class,()->appointmentService.saveAppointment(appointment));
    }

    @Test
    public void updateAppointmentWithPastDate_Test(){
        Appointment appointment1=new Appointment();
        appointment1.setAppointmentDate(LocalDate.of(2022,04,20));
        assertThrows(PastDateException.class,()->appointmentService.updateAppointment(appointment1));
    }

    @Test
    public void updateAppointment_NotFound_Test(){
        when(appointmentRepository.findById("12345")).thenReturn(Optional.empty());
        assertThrows(AppointmentNotFoundException.class,()->appointmentService.updateAppointment(appointment));
    }

    /*
    @Test
    public void updateAppointment_Test() throws ScheduleNotFoundException, PastDateException, AppointmentNotFoundException {
        Appointment appointment1=new Appointment();
        appointment1.setScheduleId("123");
        appointment1.setAppointmentId("12345");
        appointment1.setAppointmentDate(LocalDate.now());
        appointment1.setAppointmentStatus(AppointmentStatus.CANCELLED);
        when(appointmentRepository.findById(appointment1.getAppointmentId())).thenReturn(Optional.of(appointment1));
        when(appointmentRepository.save(appointment1)).thenReturn(appointment1);
        when(scheduleService.getScheduleByScheduleId(appointment1.getScheduleId())).thenReturn(schedule);
        when(scheduleService.updateSchedule(schedule)).thenReturn(schedule);
        assertEquals(appointment1,appointmentService.updateAppointment(appointment1));
    }

*/

}
