package com.stackroute.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdGeneratorServiceImpl implements IdGeneratorService{

    /*
   This method will generate random id for both schedule and appointment.
 */
    public String generateId(){
        return UUID.randomUUID().toString();
    }
}
