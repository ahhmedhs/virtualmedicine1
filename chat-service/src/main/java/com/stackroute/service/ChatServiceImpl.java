package com.stackroute.service;
import com.stackroute.exception.ChatNotFoundException;
import com.stackroute.exception.CredentialAlreadyExistedException;
import com.stackroute.model.Chat;
import com.stackroute.model.Message;
import com.stackroute.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
@Service
public class ChatServiceImpl implements ChatService{


    @Autowired
    ChatRepository chatRepository;

    /*Method to create a new chatRoom*/

    public Chat createChatRoom(Chat newChat){
       // Chat chat = new Chat();

//        if(
//                chatRepository.findByDoctorEmail(newChat.getDoctorEmail()) != null ||
//                chatRepository.findByPatientEmail(newChat.getPatientEmail()) != null ||
//                chatRepository.findByAppointmentId(newChat.getAppointmentId()) != null){
//
//            throw  new CredentialAlreadyExistedException("Credentails already existed");
//        }
        if(
                        chatRepository.findByAppointmentId(newChat.getAppointmentId()) != null){

            throw  new CredentialAlreadyExistedException("Credentails already existed");
        }

        if(newChat.getChatId() == null)
            newChat.setChatId(UUID.randomUUID().toString());

//        chat.setAppointmentId(newChat.getAppointmentId());
//        chat.setDoctorEmail(newChat.getDoctorEmail());
//        chat.setPatientEmail(newChat.getPatientEmail());
//        chat.setMessages(new ArrayList<>());
        chatRepository.save(newChat);
        return newChat;
    }

    /*Method to retrieve chat by appointmentId*/

    public Chat getChatByAppointmentId(String appointmentId){

        Chat chat = chatRepository.findByAppointmentId(appointmentId);

        if(chat == null){
            throw new ChatNotFoundException("Chat not found");
        }
       return chat;
    }

    /*Method to update current chat*/

    public Chat updateChat(String chatId, Message message){

       Optional<Chat> chat = chatRepository.findById(chatId);
       Chat newChat = null;

        if(chat.isPresent()){
            newChat = chat.get();
           newChat.getMessages().add(message);
           chatRepository.save(newChat);
       }

       else {
           throw new ChatNotFoundException("Chat not found");
        }

       return newChat;

    }

    /*Method to retrieve chat by doctorEmail*/

    public Chat findByDoctor(String doctorEmail){

        Chat chat = chatRepository.findByDoctorEmail(doctorEmail);

        if(chat == null){
            throw new ChatNotFoundException("Chat not found");
        }

        return chat;

    }

    /*Method to retrieve chat by patientEmail*/

    public Chat findByPatient(String patientEmail){

        Chat chat = chatRepository.findByPatientEmail(patientEmail);

        if(chat == null){
            throw new ChatNotFoundException("Chat not found");
        }

        return chat;

    }


}
