package com.stackroute.service;

import com.stackroute.Repository.PatientRepository;
import com.stackroute.Service.PatientServiceImpl;
import com.stackroute.exception.PatientAlreadyExistsException;
import com.stackroute.exception.PatientNotFoundException;
import com.stackroute.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

    @SpringBootTest
    public class PatientServiceImplTest {
        @Mock
        private PatientRepository patientRepository;
        @InjectMocks
        private PatientServiceImpl patientService;
        private List<Patient> patientList;
        private List<Patient> patientByEmailId;
        private Patient patient;

        @BeforeEach
        public void setUp() {
            patient = new Patient("ram123@gmail.com", "", "ram ", "9899776655", "pune", null, "no");
            Patient patient1 = new Patient("shyam123@gmail.com", "", "shyam", "9999776656", "goa", null, "no");
            Patient patient2 = new Patient("hari@gmail.com", "", "hari", "9899776657", "Ap", null, "yes");
            Patient patient3 = new Patient("laxman@gmail.com", "", "laxman", "9899776658", "Mp", null, "yes");

            patientList = new ArrayList<>();
            patientList.add(patient);
            patientList.add(patient1);
            patientList.add(patient2);
            patientList.add(patient3);


            patientByEmailId = new ArrayList<>();
            patientByEmailId.add(patient1);
            patientByEmailId.add(patient2);
        }
        @Test
        public void testSavePatient() {
            Patient patient = new Patient("ram@gmail.com", "", "ram", "9899776655", "pune", null, "no");

            when(patientRepository.save(patient)).thenReturn(patient);

        }
        @Test
        public void testFindByEmailId() {

            when(patientRepository.findByEmail("shyam123@gmail.com")).thenReturn(patient);
        }
        @Test
        public void testUpdatePatientFound() throws PatientAlreadyExistsException {
            when(patientRepository.findById("shyam123@gmail.com")).thenReturn(Optional.of(patient));
            when(patientRepository.save(patient)).thenReturn(patient);

            assertEquals(patient,patientService.updatePatient(patient));
        }

        @Test
        public void testUpdatePatientNotFound() throws PatientNotFoundException {
            when(patientRepository.findById("shyam@gmail.com")).thenReturn(Optional.empty());
           patient.setEmail(null);
        }
    }
