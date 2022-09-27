package com.stackroute.service;

import com.stackroute.model.Chat;
import com.stackroute.repository.ChatRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ChatServiceTest {

    @Mock
    ChatRepository chatRepository;

    @InjectMocks
    ChatServiceImpl chatService;

    @Test
    void CreateChatRoomTest(){
        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());

        when(chatRepository.save(chat)).thenReturn(chat);

        assertEquals(chat,chatService.createChatRoom(chat));

    }

    @Test
    void getChatByAppointmentIdTest(){

        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());
        String appointmentId = "123";
        when(chatRepository.findByAppointmentId(appointmentId)).thenReturn(chat);
        assertEquals(chat,chatService.getChatByAppointmentId(appointmentId));

    }

    @Test
    void getChatByPatientEmailTest(){

        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());
        String patientEmail = "b@gmail.com";
        when(chatRepository.findByPatientEmail(patientEmail)).thenReturn(chat);
        assertEquals(chat,chatService.findByPatient(patientEmail));

    }

    @Test
    void getChatByDoctorEmailTest(){

        Chat chat = new Chat("1","123","a@gmail.com","b@gmail.com",new ArrayList<>());
        String doctorEmail = "a@gmail.com";
        when(chatRepository.findByPatientEmail(doctorEmail)).thenReturn(chat);
        assertEquals(chat,chatService.findByPatient(doctorEmail));

    }



}
