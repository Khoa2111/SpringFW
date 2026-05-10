package com.khoa2111.appointmentbooking.appointment.api;

import com.khoa2111.appointmentbooking.appointment.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusRequest(
    @NotNull AppointmentStatus status
) {
}
