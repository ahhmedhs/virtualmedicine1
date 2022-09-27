package com.stackroute.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.Controller.PatientController;
import com.stackroute.Service.PatientService;
import com.stackroute.exception.PatientAlreadyExistsException;
import com.stackroute.exception.PatientNotFoundException;
import com.stackroute.model.Patient;
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
public class PatientControllerTest {
    @Mock
    private PatientService patientService;
    @InjectMocks
    private PatientController patientController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    private Patient patient;
    private List<Patient> patientList;
    private String baseUrl = "/api/v1/patient/";

    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(patientController).build();

        patient = new Patient("ram123@gmail.com", "", "ram ", "9899776655", "pune", null, "no");

        List<Patient>patientList;
        List<Patient>patientByEmailId;
    }

    @Test
    public void patientAlreadyExistsTest() throws Exception {
        when(patientService.savePatient(any())).thenThrow(PatientAlreadyExistsException.class);

        mockMvc.perform(post(baseUrl+"patient").content(mapper.writeValueAsString(patient))
                .contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void updatePatientest() throws Exception {
        when(patientService.updatePatient(any())).thenReturn(patient);

        mockMvc.perform(put(baseUrl+"patient").content(mapper.writeValueAsString(patient))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updatePatientNotFoundTest() throws Exception {
        when(patientService.updatePatient(any())).thenThrow(PatientNotFoundException.class);

        mockMvc.perform(put(baseUrl+"patient").content(mapper.writeValueAsString(patient))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(print());
    }

}

