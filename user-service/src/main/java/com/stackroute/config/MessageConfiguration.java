package com.stackroute.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class MessageConfiguration {
        private String exchangeName="user_exchange";

        @Bean
        public DirectExchange directExchange()
        {
            return new DirectExchange(exchangeName);
        }

        @Bean
        public Jackson2JsonMessageConverter producerJackson2MessageConverter()
        {
            return new  Jackson2JsonMessageConverter();
        }

        @Bean
        public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory)
        {
            RabbitTemplate rabbitTemp=new RabbitTemplate(connectionFactory);
            rabbitTemp.setMessageConverter(producerJackson2MessageConverter());
            return rabbitTemp;
        }




}



