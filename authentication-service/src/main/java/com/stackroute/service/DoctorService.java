package com.stackroute.service;

import com.stackroute.model.Doctor;

import java.util.Map;

public interface DoctorService {

     Doctor addDoctor(Doctor doctor);

     Map<String,String> doctorLogin(Doctor doctor);
}
