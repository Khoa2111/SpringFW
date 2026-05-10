package com.khoa2111.appointmentbooking.appointment.api;

import java.time.Instant;

public record AppointmentResponse(
    Long id,
    Long doctorId,
    Long patientId,
    Instant startTime,
    Instant endTime,
    String status
) {
}
