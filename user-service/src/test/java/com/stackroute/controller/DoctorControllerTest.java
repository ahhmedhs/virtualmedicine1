package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackroute.Controller.DoctorController;
import com.stackroute.Service.DoctorService;
import com.stackroute.exception.DoctorAlreadyExistsException;
import com.stackroute.exception.DoctorNotFoundException;
import com.stackroute.model.Doctor;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.stackroute")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Doctor doctor;
    private List<Doctor> doctorsList;
    private String baseUrl = "/api/v1/doctor/";

    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(doctorController).build();


        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        doctor = new Doctor("sweetysahoo@gmail.com", "","sweety sahoo", "Oncology", "8899776655",54321L,"pune",2.1,null);


      List<Doctor>doctorList;
      List<Doctor>doctorByEmailId;
    }


    @Test
    public void doctorAlreadyExistsTest() throws Exception {
        when(doctorService.saveDoctor(any())).thenThrow(DoctorAlreadyExistsException.class);

        mockMvc.perform(post(baseUrl+"doctor").content(mapper.writeValueAsString(doctor))
                .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void updateDoctorTest() throws Exception {
        when(doctorService.updateDoctor(any())).thenReturn(doctor);

        mockMvc.perform(put(baseUrl+"doctor").content(mapper.writeValueAsString(doctor))
              .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateDoctorNotFoundTest() throws Exception {
        when(doctorService.updateDoctor(any())).thenThrow(DoctorNotFoundException.class);

        mockMvc.perform(put(baseUrl+"doctor").content(mapper.writeValueAsString(doctor))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
    }

    }






