package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackroute.exception.AppointmentAlreadyExistsException;
import com.stackroute.exception.AppointmentIdNotNullException;
import com.stackroute.exception.AppointmentNotFoundException;
import com.stackroute.exception.PastDateException;
import com.stackroute.model.Appointment;
import com.stackroute.model.AppointmentStatus;
import com.stackroute.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.stackroute")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Appointment appointment;
    private List<Appointment> appointmentList;
    private String baseUrl = "/api/v1/appointments/";

    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(appointmentController).build();


        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        appointment=new Appointment("shoeb.khan@gmail.com","saqib@gmail.com",LocalDate.now(),"123",LocalTime.now(),LocalTime.now(), AppointmentStatus.BOOKED,"Nothing as such","MD",LocalDate.now());

        appointmentList=new ArrayList<>();
        appointmentList.add(appointment);
    }

//    @Test
//    public void retrieveAllAppointmentsByPatientEmail_Test() throws Exception {
//        when(appointmentService.getAllAppointmentsByPatientEmail("shoeb.khan@gmail.com")).thenReturn(appointmentList);
//
//        mockMvc.perform(get(baseUrl+"appointment/patient/{patientEmail}",appointment.getPatientEmail()))
//                .andExpect(status().isFound());
//    }

//    @Test
//    public void retrieveAppointmentByAppointmentId_Test() throws Exception {
//        when(appointmentService.getAppointmentByAppointmentId("123")).thenReturn(appointment);
//
//        mockMvc.perform(get(baseUrl+"appointment/{appointmentId}","123"))
//                .andExpect(status().isFound())
//                .andExpect(MockMvcResultMatchers.jsonPath(".patientEmail").value("shoeb.khan@gmail.com"));
//    }

    @Test
    public void retrieveAppointmentByAppointmentIdNotFound_Test() throws Exception {
        when(appointmentService.getAppointmentByAppointmentId("123")).thenThrow(AppointmentNotFoundException.class);

        mockMvc.perform(get(baseUrl+"appointment/{appointmentId}","123"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void retrieveAllAppointmentsByDoctorEmail_Test() throws Exception {
//        when(appointmentService.getAllAppointmentsByDoctorEmail("saqib@gmail.com")).thenReturn(appointmentList);
//
//        mockMvc.perform(get(baseUrl+"appointment/doctor/{patientEmail}",appointment.getPatientEmail()))
//                .andExpect(status().isFound());
//    }

//    @Test
//    public void retrieveAppointmentByScheduleId_Test() throws Exception {
//        when(appointmentService.getAppointmentByScheduleId("123")).thenReturn(appointment);
//
//        mockMvc.perform(get(baseUrl+"appointment/schedule/{scheduleId}","123"))
//                .andExpect(status().isFound())
//                .andExpect(MockMvcResultMatchers.jsonPath(".patientEmail").value("shoeb.khan@gmail.com"));
//    }

    @Test
    public void retrieveAppointmentByScheduleIdNotFound_Test() throws Exception {
        when(appointmentService.getAppointmentByScheduleId("123")).thenReturn(null);

        mockMvc.perform(get(baseUrl+"appointment/schedule/{scheduleId}","123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void cancelAppointment_Test() throws Exception {
        when(appointmentService.getAppointmentByAppointmentId("123")).thenReturn(appointment);
        when(appointmentService.updateAppointment(appointment)).thenReturn(appointment);


        mockMvc.perform(delete(baseUrl + "appointment/cancel/{appointmentId}","123"))
                .andExpect(status().isOk());
    }

    @Test
    public void cancelAppointmentNotFound_Test() throws Exception {
        when(appointmentService.getAppointmentByAppointmentId("123")).thenReturn(null);

        mockMvc.perform(delete(baseUrl+"appointment/cancel/{appointmentId}","123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void bookAppointment_Test() throws Exception {
        when(appointmentService.saveAppointment(any())).thenReturn(appointment);

        mockMvc.perform(post(baseUrl+"appointment").content(mapper.writeValueAsString(appointment))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void bookAppointmentIdNotNull_Test() throws Exception {
        when(appointmentService.saveAppointment(any())).thenThrow(AppointmentIdNotNullException.class);

        mockMvc.perform(post(baseUrl+"appointment").content(mapper.writeValueAsString(appointment))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void bookAppointmentAlreadyExists_Test() throws Exception {
        when(appointmentService.saveAppointment(any())).thenThrow(AppointmentAlreadyExistsException.class);

        mockMvc.perform(post(baseUrl+"appointment").content(mapper.writeValueAsString(appointment))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict()).andDo(print());
    }

    @Test
    public void bookAppointmentNullPointer_Test() throws Exception {

        mockMvc.perform(post(baseUrl+"appointment")).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void updateAppointment_Test() throws Exception {
        when(appointmentService.updateAppointment(any())).thenReturn(appointment);

        mockMvc.perform(put(baseUrl+"appointment").content(mapper.writeValueAsString(appointment))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void updateAppointmentNotFound_Test() throws Exception {
        when(appointmentService.updateAppointment(any())).thenThrow(AppointmentNotFoundException.class);

        mockMvc.perform(put(baseUrl+"appointment").content(mapper.writeValueAsString(appointment))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void updateAppointmentWithPastDate_Test() throws Exception {
        when(appointmentService.updateAppointment(any())).thenThrow(PastDateException.class);

        mockMvc.perform(put(baseUrl+"appointment").content(mapper.writeValueAsString(appointment))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print());
    }


}
