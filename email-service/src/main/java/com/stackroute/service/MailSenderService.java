package com.stackroute.service;

import com.stackroute.model.AppointmentStatus;
import com.stackroute.model.MQMessage;
import org.springframework.stereotype.Service;

@Service
public interface MailSenderService {

    void messageListener(MQMessage message);
    void packageAndSend(MQMessage message);
    void sendMail(String to, String body, AppointmentStatus status);
}
