package com.khoa2111.appointmentbooking.appointment.api;

import com.khoa2111.appointmentbooking.appointment.Appointment;

public class AppointmentMapper {
  public static AppointmentResponse toResponse(Appointment a) {
    return new AppointmentResponse(
        a.getId(),
        a.getDoctor().getId(),
        a.getPatient().getId(),
        a.getStartTime(),
        a.getEndTime(),
        a.getStatus().name()
    );
  }
}
