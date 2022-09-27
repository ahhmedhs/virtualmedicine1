package com.stackroute.service;

import com.stackroute.model.Chat;
import com.stackroute.model.Message;
import com.stackroute.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
//@Service
//public class ChatService {
//
//
//    @Autowired
//    ChatRepository chatRepository;
//
//    public Chat createChatRoom(Chat newChat) {
//        Chat chat = new Chat();
//
////        if(chatRepository.findByDoctorEmail(newChat.getDoctorEmail()) != null ||
////                chatRepository.findByPatientEmail(newChat.getPatientEmail()) != null ||
////                chatRepository.findByAppointmentId(newChat.getAppointmentId()) != null){
////
////            throw  new CredentialAlreadyExistedException("Credentails already existed");
////        }
//        if (chatRepository.findByAppointmentId(newChat.getAppointmentId()) != null) {
//
//            throw new CredentialAlreadyExistedException("Credentails already existed");
//        }
//
//        chat.setChatId(UUID.randomUUID().toString());
//        chat.setAppointmentId(newChat.getAppointmentId());
//        chat.setDoctorEmail(newChat.getDoctorEmail());
//        chat.setPatientEmail(newChat.getPatientEmail());
//        chat.setMessages(new ArrayList<>());
//        chatRepository.save(chat);
//        return chat;
//    }
//
//
//    public Chat getChatByAppointmentId(String appointmentId) {
//
//        Chat chat = chatRepository.findByAppointmentId(appointmentId);
//
//        if (chat == null) {
//            throw new ChatNotFoundException("Chat not found");
//        }
//        return chat;
//    }
//
//    public Chat updateChat(String chatId, Message message) {
//
//        Optional<Chat> chat = chatRepository.findById(chatId);
//        Chat newChat = null;
//
//        if (chat.isPresent()) {
//            newChat = chat.get();
//            newChat.getMessages().add(message);
//            chatRepository.save(newChat);
//        } else {
//            throw new ChatNotFoundException("Chat not found");
//        }
//
//        return newChat;
//
//    }
//
//    public Chat findByDoctor(String doctorEmail) {
//
//        Chat chat = chatRepository.findByDoctorEmail(doctorEmail);
//
//        if (chat == null) {
//            throw new ChatNotFoundException("Chat not found");
//        }
//
//        return chat;
//    }
//}
public interface ChatService {

     Chat createChatRoom(Chat newChat);

     Chat getChatByAppointmentId(String appointmentId);

     Chat updateChat(String chatId, Message message);

     Chat findByDoctor(String doctorEmail);

     Chat findByPatient(String patientEmail);


}
