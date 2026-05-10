package com.khoa2111.appointmentbooking.patient.api;

import jakarta.validation.constraints.NotBlank;

public record CreatePatientRequest(
    @NotBlank String name,
    @NotBlank String phone
) {
}
