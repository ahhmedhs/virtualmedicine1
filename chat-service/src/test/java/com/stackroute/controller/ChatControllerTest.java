package com.stackroute.controller;

import com.stackroute.model.Chat;
import com.stackroute.repository.ChatRepository;
import com.stackroute.service.ChatService;
import com.stackroute.service.ChatServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ChatControllerTest {

    @Mock
    ChatServiceImpl chatService;


    @InjectMocks
    ChatController chatController;



    @Test
    void createRoomTest(){

        Chat chat = new Chat("123","a@gmail.com","b@gmail.com");

        when(chatService.createChatRoom(chat)).thenReturn(chat);

        ResponseEntity<Chat> res = chatController.createChatRoom(chat);

        assertEquals(HttpStatus.CREATED,res.getStatusCode());

        assertEquals(chat,res.getBody());

    }

    @Test
    void getChatByAppointmentIdTest(){
        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());

        String appointmentId = "123";

        when(chatService.getChatByAppointmentId(appointmentId)).thenReturn(chat);

        ResponseEntity<Chat> res = chatController.getChatById(appointmentId);

        assertEquals(HttpStatus.FOUND,res.getStatusCode());


    }

    @Test
    void getChatByDoctorEmailTest(){
        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());

        String doctorEmail = "a@gmail.com";

        when(chatService.findByDoctor(doctorEmail)).thenReturn(chat);

        ResponseEntity<Chat> res = chatController.getChatById(doctorEmail);

        assertEquals(HttpStatus.FOUND,res.getStatusCode());


    }

    @Test
    void getChatByPAtientEmailTest(){
        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());

        String patientEmail = "b@gmail.com";

        when(chatService.findByDoctor(patientEmail)).thenReturn(chat);

        ResponseEntity<Chat> res = chatController.getChatById(patientEmail);

        assertEquals(HttpStatus.FOUND,res.getStatusCode());


    }


}
