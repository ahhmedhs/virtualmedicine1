package com.stackroute.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;
import java.time.LocalTime;


@Document(collection = "schedule")
@ApiModel("Schedule Model Class")
public class Schedule  {

        @Id
        private String scheduleId;
        private String doctorEmail;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate scheduleDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private ScheduleStatus scheduleStatus;
        private String specialization;

        public Schedule() {
        }

        public Schedule(String doctorEmail, LocalDate scheduleDate, LocalTime startTime, LocalTime endTime, ScheduleStatus scheduleStatus, String specialization) {
                this.doctorEmail = doctorEmail;
                this.scheduleDate = scheduleDate;
                this.startTime = startTime;
                this.endTime = endTime;
                this.scheduleStatus = scheduleStatus;
                this.specialization = specialization;
        }

        public String getScheduleId() {
                return scheduleId;
        }

        public void setScheduleId(String scheduleId) {
                this.scheduleId = scheduleId;
        }

        public String getDoctorEmail() {
                return doctorEmail;
        }

        public void setDoctorEmail(String doctorEmail) {
                this.doctorEmail = doctorEmail;
        }

        public LocalDate getScheduleDate() {
                return scheduleDate;
        }

        public void setScheduleDate(LocalDate scheduleDate) {
                this.scheduleDate = scheduleDate;
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

        public ScheduleStatus getScheduleStatus() {
                return scheduleStatus;
        }

        public void setScheduleStatus(ScheduleStatus scheduleStatus) {
                this.scheduleStatus = scheduleStatus;
        }

        public String getSpecialization() {
                return specialization;
        }

        public void setSpecialization(String specialization) {
                this.specialization = specialization;
        }

        @Override
        public String toString() {
                return "Schedule{" +
                        "scheduleId='" + scheduleId + '\'' +
                        ", doctorEmail='" + doctorEmail + '\'' +
                        ", date=" + scheduleDate +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", status=" + scheduleStatus +
                        ", specialization='" + specialization + '\'' +
                        '}';
        }
}
