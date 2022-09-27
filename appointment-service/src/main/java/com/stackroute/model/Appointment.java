package com.stackroute.model;


import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import java.time.LocalDate;
import java.time.LocalTime;


@Document(collection = "appointment")
@ApiModel("Appointment Model Class")
public class Appointment {

    @Id
    private String appointmentId;
    private String patientEmail;
    private String doctorEmail;
    private LocalDate appointmentDate;
    private String scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;
    private AppointmentStatus appointmentStatus;
    private String patientQuery;
    private String specialization;
    private LocalDate bookedOn;


    public Appointment() {
    }

    public Appointment(String patientEmail, String doctorEmail, LocalDate appointmentDate, String scheduleId, LocalTime startTime, LocalTime endTime, AppointmentStatus appointmentStatus, String patientQuery, String specialization, LocalDate bookedOn) {

        this.patientEmail = patientEmail;
        this.doctorEmail = doctorEmail;
        this.appointmentDate = appointmentDate;
        this.scheduleId = scheduleId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentStatus = appointmentStatus;
        this.patientQuery = patientQuery;
        this.specialization = specialization;
        this.bookedOn = bookedOn;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getPatientQuery() {
        return patientQuery;
    }

    public void setPatientQuery(String patientQuery) {
        this.patientQuery = patientQuery;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(LocalDate bookedOn) {
        this.bookedOn = bookedOn;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", patientEmail='" + patientEmail + '\'' +
                ", doctorEmail='" + doctorEmail + '\'' +
                ", date=" + appointmentDate +
                ", scheduleId='" + scheduleId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + appointmentStatus +
                ", patientQuery='" + patientQuery + '\'' +
                ", specialization='" + specialization + '\'' +
                ", bookedOn=" + bookedOn +
                '}';
    }
}
