package com.stackroute.config;

import com.stackroute.rabbitMq.DoctorDTO;
import com.stackroute.rabbitMq.PatientDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
    public class Producer {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessageToRabbitMq(DoctorDTO doctorDTO) {
        rabbitTemplate.convertAndSend(exchange.getName(), "doctor_routing", doctorDTO);
    }


    public void sendMessageToRabbitMq(PatientDTO patientDTO) {
        rabbitTemplate.convertAndSend(exchange.getName(), "patient_routing", patientDTO);
    }
}










