package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackroute.exception.PastDateException;
import com.stackroute.exception.ScheduleIdNotNullException;
import com.stackroute.exception.ScheduleNotFoundException;
import com.stackroute.model.Schedule;
import com.stackroute.model.ScheduleStatus;
import com.stackroute.service.ScheduleService;
import org.junit.jupiter.api.*;

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
@ComponentScan(basePackages = "com.stackroute.*")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class ScheduleControllerTest {

    @Mock
    private ScheduleService scheduleService;
    @Autowired
    private ObjectMapper mapper;

    @InjectMocks
    private ScheduleController scheduleController;
    @Autowired
    private MockMvc mockMvc;
    private List<Schedule> scheduleList;
    private Schedule schedule;

    private String baseUrl="/api/v1/schedules/";


    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(scheduleController).build();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        schedule=new Schedule("shoeb.khan@gmail.com", LocalDate.now(), LocalTime.now(),LocalTime.of(12,40), ScheduleStatus.BOOKED,"MS");

        scheduleList=new ArrayList<>();
        scheduleList.add(schedule);
    }

    @Test
    public void createScheduleNull_Test() throws Exception {
        mockMvc.perform(post(baseUrl+"schedule")).andExpect(status().isBadRequest());
    }

//    @Test
//    public void createSchedule_Test() throws Exception {
//        when(scheduleService.saveSchedule(any())).thenReturn(schedule);
//
//        mockMvc.perform(post(baseUrl+"schedule")
//                .content(mapper.writeValueAsString(schedule))
//                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andExpect(status().isCreated()).andDo(print());
//    }

    @Test
    public void createScheduleIdNotNull_Test() throws Exception {
        Schedule schedule1=new Schedule();
        schedule1.setScheduleId("1234");
        when(scheduleService.saveSchedule(any())).thenThrow(ScheduleIdNotNullException.class);
        mockMvc.perform(post(baseUrl+"schedule")
                        .content(mapper.writeValueAsString(schedule1))
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                        .andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    public void createScheduleWithPastDate_Test() throws Exception {

        when(scheduleService.saveSchedule(any())).thenThrow(PastDateException.class);

        mockMvc.perform(post(baseUrl+"schedule")
                .content(mapper.writeValueAsString(schedule))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print());
    }

//    @Test
//    public void retrieveAllSchedulesByDoctorEmail_Test() throws Exception {
//        when(scheduleService.getAllAvailableScheduleByDoctorEmail("shoeb.khan@gmail.com")).thenReturn(scheduleList);
//
//        mockMvc.perform(get(baseUrl+"schedule/doctor/{doctorEmail}","shoeb.khan@gmail.com"))
//                .andExpect(status().isFound());
//
//    }
//    @Test
//    public void retrieveScheduleByScheduleId_Test() throws Exception {
//        when(scheduleService.getScheduleByScheduleId("123")).thenReturn(schedule);
//
//        mockMvc.perform(get(baseUrl+"schedule/{scheduleId}","123"))
//                .andExpect(status().isFound());
//
//    }

    @Test
    public void retrieveScheduleByScheduleIdNotFound_Test() throws Exception {
        when(scheduleService.getScheduleByScheduleId("123")).thenThrow(ScheduleNotFoundException.class);

        mockMvc.perform(get(baseUrl+"schedule/{scheduleId}","123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSchedule_Test() throws Exception {

        when(scheduleService.updateSchedule(any())).thenReturn(schedule);

        mockMvc.perform(put(baseUrl+"schedule")
                .content(mapper.writeValueAsString(schedule))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".doctorEmail").value("shoeb.khan@gmail.com")).andDo(print());

    }

    @Test
    public void updateScheduleNotFound_Test() throws Exception {

        when(scheduleService.updateSchedule(any())).thenThrow(ScheduleNotFoundException.class);

        mockMvc.perform(put(baseUrl+"schedule")
                        .content(mapper.writeValueAsString(schedule))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andDo(print());

    }

    @Test
    public void updateScheduleWithPastDate_Test() throws Exception {

        when(scheduleService.updateSchedule(any())).thenThrow(PastDateException.class);

        mockMvc.perform(put(baseUrl+"schedule")
                        .content(mapper.writeValueAsString(schedule))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andDo(print());

    }


}
