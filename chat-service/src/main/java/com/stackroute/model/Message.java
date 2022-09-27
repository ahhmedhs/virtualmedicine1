package com.stackroute.model;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

public class Message {

    String sender;
    String receiver;
    String content;
    LocalTime timestamp;
    MultipartFile file;

    public Message(){

    }

    public Message(String sender, String receiver, String content, LocalTime timestamp, MultipartFile file) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
        this.file = file;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
