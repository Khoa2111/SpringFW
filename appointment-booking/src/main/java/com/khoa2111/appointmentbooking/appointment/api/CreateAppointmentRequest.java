package com.khoa2111.appointmentbooking.appointment.api;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateAppointmentRequest(
    @NotNull Long doctorId,
    @NotNull Long patientId,
    @NotNull Instant startTime,
    @NotNull Instant endTime
) {
}
