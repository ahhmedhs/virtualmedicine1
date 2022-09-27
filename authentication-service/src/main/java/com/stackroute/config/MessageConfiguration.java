package com.stackroute.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {


    private String exchangeName="user_exchange";

    private String doctorQueue="doctor_queue";

    private String patientQueue="patient_queue";


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue doctorQueue()
    {
        return new Queue(doctorQueue,false);
    }

    @Bean
    public Queue patientQueue()
    {
        return new Queue(patientQueue,false);
    }



    @Bean
    Binding bindingDoctor(Queue doctorQueue, DirectExchange exchange)
    {
        return BindingBuilder.bind(doctorQueue()).to(exchange).with("doctor_routing");
    }

    @Bean
    Binding bindingPatient(Queue patientQueue, DirectExchange exchange)
    {
        return BindingBuilder.bind(patientQueue()).to(exchange).with("patient_routing");
    }

}

