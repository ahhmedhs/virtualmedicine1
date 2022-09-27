package com.stackroute.service;

import com.stackroute.Repository.DoctorRepository;
import com.stackroute.Service.DoctorServiceImpl;
import com.stackroute.exception.DoctorAlreadyExistsException;
import com.stackroute.exception.DoctorNotFoundException;
import com.stackroute.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

    @SpringBootTest
    public class DoctorDTOServiceImplTest {

       @Mock
        private DoctorRepository doctorRepository;

       @InjectMocks
       private DoctorServiceImpl doctorService;
       private List<Doctor>doctorList;
        private List<Doctor>doctorByEmailId;
        private Doctor doctor;

       @BeforeEach
        public void setUp(){
             doctor = new Doctor("sweetysahoo@gmail.com", "","sweety sahoo", "Oncology", "8899776655",54321L,"pune",2.1,null);
           Doctor doctor1 = new Doctor("swatisahoo@gmail.com", "","swati sahoo", "Cardiology", "8999776656",54322L,"goa",2.2,null);
            Doctor doctor2 = new Doctor("swetasahoo@gmail.com","","sweta sahoo", "Dermatology", "8899776657",54323L,"Ap",2.3,null);
            Doctor doctor3 = new Doctor("shrutisahoo@gmail.com","","shruti sahoo", "Neurology", "8899776658",543214L,"Mp",2.4,null);

            doctorList=new ArrayList<>();
            doctorList.add(doctor);
            doctorList.add(doctor1);
           doctorList.add(doctor2);
           doctorList.add(doctor3);


            doctorByEmailId=new ArrayList<>();
           doctorByEmailId.add(doctor1);
            doctorByEmailId.add(doctor2);
        }

        @Test
       public void testSaveDoctor(){
            Doctor doctor1 = new Doctor("swatisahoo@gmail.com", "","swati sahoo", "Cardiology", "8999776656",54322L,"goa",2.2,null);

            when(doctorRepository.save(doctor1)).thenReturn(doctor1);

       }

        @Test
        public void testFindByEmailId(){

            when(doctorRepository.findByEmail("sweetysahoo@gmail.com")).thenReturn(doctor);
            assertEquals(doctor, doctorService.findByEmailId("sweetysahoo@gmail.com"));
        }
        @Test
        public void findByCityAndSpecialization(){

            when(doctorRepository.findByCityAndSpecialization("pune","oncology")).thenReturn(Collections.emptyList());
            assertEquals(Collections.emptyList(), doctorService.findByCityAndSpecialization("pune","oncology"));
        }

        @Test
        public void testUpdateDoctorFound() throws DoctorAlreadyExistsException{
            when(doctorRepository.findById("sweetysahoo@gmail.com")).thenReturn(Optional.of(doctor));
            when(doctorRepository.save(doctor)).thenReturn(doctor);

            assertEquals(doctor,doctorService.updateDoctor(doctor));
        }

        @Test
        public void testUpdateDoctorNotFound() throws DoctorNotFoundException {
            when(doctorRepository.findById("sweetysahoo@gmail.com")).thenReturn(Optional.empty());
            doctor.setEmail(null);

            assertThrows(DoctorNotFoundException.class,()->doctorService.updateDoctor(doctor));
        }
    }
