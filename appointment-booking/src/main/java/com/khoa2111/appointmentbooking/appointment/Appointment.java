package com.khoa2111.appointmentbooking.appointment;

import com.khoa2111.appointmentbooking.doctor.Doctor;
import com.khoa2111.appointmentbooking.patient.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
    name = "appointments",
    indexes = {
        @Index(name = "idx_appointments_doctor_start", columnList = "doctor_id,start_time"),
        @Index(name = "idx_appointments_doctor_end", columnList = "doctor_id,end_time")
    }
)
@Getter
@Setter
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_id")
  private Patient patient;

  @Column(name = "start_time", nullable = false)
  private Instant startTime;

  @Column(name = "end_time", nullable = false)
  private Instant endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatus status = AppointmentStatus.SCHEDULED;
}
