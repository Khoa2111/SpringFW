package com.khoa2111.appointmentbooking.doctor.api;

import jakarta.validation.constraints.NotBlank;

public record CreateDoctorRequest(
    @NotBlank String name,
    @NotBlank String specialty
) {
}
