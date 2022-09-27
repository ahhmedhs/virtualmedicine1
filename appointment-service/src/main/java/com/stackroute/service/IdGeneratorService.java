package com.stackroute.service;

import org.springframework.stereotype.Service;

@Service
public interface IdGeneratorService {

    /*This method will generate random UUID*/
     String generateId();
}
