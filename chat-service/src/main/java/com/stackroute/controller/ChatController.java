package com.stackroute.controller;

import com.stackroute.exception.ChatNotFoundException;
import com.stackroute.exception.CredentialAlreadyExistedException;
import com.stackroute.model.Chat;
import com.stackroute.model.Message;
import com.stackroute.repository.ChatRepository;
import com.stackroute.service.ChatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ChatController {

    @Autowired
    ChatServiceImpl chatService;

    @Autowired
    ChatRepository chatRepository;

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    /* Create API endpoints as per the requirements given below
     */

    /*
    description : create new chat
    api endpoint : /chat
    http request : POST
    request body : chat details
    success response : Created:201
    failure response : Bad Request : 400
    */

    @PostMapping("/chat")
    public ResponseEntity<Chat> createChatRoom(@RequestBody Chat chat){
        ResponseEntity<Chat> responseEntity;

        try{
            Chat newChat = chatService.createChatRoom(chat);
            responseEntity = new ResponseEntity<>(newChat , HttpStatus.CREATED);
        } catch (CredentialAlreadyExistedException exception){
            logger.error("Credentials already existed",exception);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

     /*
    description : get chat by appointmentId
    api endpoint : /chat/appointmentId/{appointmentId}
    http request : GET
    success response : Found : 302
    failure response : Bad Request : 400
    */

    @GetMapping("/chat/appointmentId/{appointmentId}")
    public ResponseEntity<Chat> getChatById(@PathVariable String appointmentId){
        ResponseEntity<Chat> responseEntity;
        try {
        Chat chat = chatService.getChatByAppointmentId(appointmentId);
        responseEntity = new ResponseEntity<>(chat, HttpStatus.FOUND);
        }
        catch (ChatNotFoundException exception){
            logger.error("Chat not found",exception);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

     /*
    description : update chat by chatId
    api endpoint : /chat/chatId/{chatId}
    http request : GET
    request body : Updated Message
    success response : Created:201
    failure response : Bad Request : 400
    */

    @PutMapping("/chat/chatId/{chatId}")
    public ResponseEntity<Chat> updateChat(@PathVariable String chatId, @RequestBody Message message){
        ResponseEntity<Chat> responseEntity;

        Optional<Chat> chat = chatRepository.findById(chatId);

        if(chat.isPresent()){
            Chat newChat = chatService.updateChat(chatId,message);
            responseEntity = new ResponseEntity<>(newChat,HttpStatus.CREATED);
        }
        else{
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

     /*
    description : get chat by doctorEmail
    api endpoint : /chat/doctorEmail/{doctorEmail}
    http request : GET
    success response : Found : 302
    failure response : Bad Request : 400
    */

    @GetMapping("/chat/doctorEmail/{doctorEmail}")
    public ResponseEntity<Chat> getChatByDoctorEmail(@PathVariable String doctorEmail){
        ResponseEntity<Chat> responseEntity;
        try {
            Chat chat = chatService.findByDoctor(doctorEmail);
            responseEntity = new ResponseEntity<>(chat, HttpStatus.FOUND);
        }
        catch (ChatNotFoundException exception){
            logger.error("Chat not found",exception);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }


     /*
    description : get chat by patientEmail
    api endpoint : /chat/patientEmail/{patientEmail}
    http request : GET
    success response : Found : 302
    failure response : Bad Request : 400
    */

    @GetMapping("/chat/patientEmail/{patientEmail}")
    public ResponseEntity<Chat> getChatByPatientEmail(@PathVariable String patientEmail){
        ResponseEntity<Chat> responseEntity;
        try {
            Chat chat = chatService.findByPatient(patientEmail);
            responseEntity = new ResponseEntity<>(chat, HttpStatus.FOUND);
        }
        catch (ChatNotFoundException exception){
            logger.error("Chat not found",exception);
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

}
