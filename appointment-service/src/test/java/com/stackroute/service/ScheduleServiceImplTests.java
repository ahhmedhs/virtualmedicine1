package com.stackroute.service;

import com.stackroute.exception.PastDateException;
import com.stackroute.exception.ScheduleAlreadyExistsException;
import com.stackroute.exception.ScheduleIdNotNullException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Appointment;
import com.stackroute.model.Schedule;
import com.stackroute.model.ScheduleStatus;
import com.stackroute.repository.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScheduleServiceImplTests {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private IdGeneratorService idGeneratorService;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private List<Schedule> scheduleList;
    private List<Schedule> scheduleById;
    private Schedule schedule;

    @BeforeEach
    public void setUp(){
        schedule=new Schedule("shoeb.khan@gmail.com",LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");
        schedule.setScheduleId("123");

        Schedule schedule1=new Schedule("shoeb@gmail.com",LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");
        schedule1.setScheduleId("124");
        Schedule schedule2=new Schedule("shoeb@gmail.com",LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");
        schedule2.setScheduleId("125");
        Schedule schedule3=new Schedule("shoeb.khan@gmail.com",LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");
        schedule3.setScheduleId("126");

        scheduleList=new ArrayList<>();
        scheduleList.add(schedule);
        scheduleList.add(schedule1);
        scheduleList.add(schedule2);
        scheduleList.add(schedule3);

        scheduleById=new ArrayList<>();
        scheduleById.add(schedule1);
        scheduleById.add(schedule2);


    }

    @Test
    public void testGetAllScheduleByDoctorEmail(){

        when(scheduleRepository.findAllAvailableScheduleByDoctorEmail("shoeb@gmail.com")).thenReturn(scheduleById);
        assertEquals(scheduleById,scheduleService.getAllAvailableScheduleByDoctorEmail("shoeb@gmail.com"));
    }

    @Test
    public void testGetAllScheduleByDoctorEmailNotFound(){

        when(scheduleRepository.findAllAvailableScheduleByDoctorEmail("shoeb123@gmail.com")).thenReturn(Collections.EMPTY_LIST);
        assertEquals(Collections.EMPTY_LIST,scheduleService.getAllAvailableScheduleByDoctorEmail("shoeb123@gmail.com"));
    }

    @Test
    public void testGetScheduleByScheduleIdFound() throws ScheduleNotFoundException {
        when(scheduleRepository.findById("123")).thenReturn(Optional.of(schedule));

        assertEquals(schedule,scheduleService.getScheduleByScheduleId("123"));
    }

    @Test
    public void testGetScheduleByScheduleIdNotFound() throws ScheduleNotFoundException {
        when(scheduleRepository.findById("122")).thenReturn(Optional.empty());

        assertThrows(ScheduleNotFoundException.class,()->scheduleService.getScheduleByScheduleId("122"),"Schedule is not present in the database.");
    }

    /*
    @Test
    public void testSaveSchedule() throws PastDateException, ScheduleIdNotNullException, ScheduleAlreadyExistsException {
        Schedule schedule1=new Schedule("shoeb@gmail.com",LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");
         when(idGeneratorService.generateId()).thenReturn("123");
         when(scheduleRepository.save(schedule1)).thenReturn(schedule1);
         when(scheduleRepository.findByDoctorEmailAndScheduleDateAndStartTimeAndEndTime(schedule1.getDoctorEmail(),schedule1.getScheduleDate(),schedule1.getStartTime(),schedule1.getEndTime())).thenReturn(null);
         assertEquals(schedule1,scheduleService.saveSchedule(schedule1));
    }
    */

    @Test
    public void testSaveScheduleIdNotNull() throws PastDateException, ScheduleIdNotNullException {
        Schedule schedule1=new Schedule("shoeb@gmail.com",LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");
        schedule1.setScheduleId("123");

        assertThrows(ScheduleIdNotNullException.class,()->scheduleService.saveSchedule(schedule1));
    }

    @Test
    public void testSaveScheduleWithPastDates() throws PastDateException, ScheduleIdNotNullException {
        Schedule schedule1=new Schedule("shoeb@gmail.com",LocalDate.of(2022,04,20), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");

        assertThrows(PastDateException.class,()->scheduleService.saveSchedule(schedule1));
    }


    @Test
    public void testUpdateScheduleFound() throws ScheduleNotFoundException, PastDateException {
        Appointment appointment=new Appointment();
        appointment.setAppointmentId("12345");
        when(scheduleRepository.findById("123")).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(schedule)).thenReturn(schedule);
        when(appointmentService.getAppointmentByScheduleId("123")).thenReturn(appointment);
        when(appointmentService.updateAppointmentProxy(appointment)).thenReturn(appointment);

        assertEquals(schedule,scheduleService.updateSchedule(schedule));
    }

    @Test
    public void testUpdateScheduleNotFound() throws ScheduleNotFoundException {
        when(scheduleRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ScheduleNotFoundException.class,()->scheduleService.updateSchedule(schedule),"Schedule is not present in database.");
    }
}
