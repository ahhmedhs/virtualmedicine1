package com.stackroute.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chat")
public class Chat {

    @Id
    private String chatId;

    private String appointmentId;

    private String doctorEmail;
    private String patientEmail;
    List<Message> messages;

    public Chat(){

    }

    public Chat(String chatId, String appointmentId, String doctorEmail, String patientEmail, List<Message> messages) {
        this.chatId = chatId;
        this.appointmentId = appointmentId;
        this.doctorEmail = doctorEmail;
        this.patientEmail = patientEmail;
        this.messages = messages;
    }

    public Chat(String appointmentId,String doctorEmail,String patientEmail){
        this.appointmentId = appointmentId;
        this.doctorEmail = doctorEmail;
        this.patientEmail = patientEmail;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
