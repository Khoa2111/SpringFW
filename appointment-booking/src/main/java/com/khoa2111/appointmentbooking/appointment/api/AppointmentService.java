package com.khoa2111.appointmentbooking.appointment.api;

import com.khoa2111.appointmentbooking.appointment.Appointment;
import com.khoa2111.appointmentbooking.appointment.AppointmentRepository;
import com.khoa2111.appointmentbooking.appointment.AppointmentStatus;
import com.khoa2111.appointmentbooking.doctor.DoctorRepository;
import com.khoa2111.appointmentbooking.patient.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  public AppointmentService(AppointmentRepository appointmentRepository,
                            DoctorRepository doctorRepository,
                            PatientRepository patientRepository) {
    this.appointmentRepository = appointmentRepository;
    this.doctorRepository = doctorRepository;
    this.patientRepository = patientRepository;
  }

  @Transactional
  public Appointment create(CreateAppointmentRequest req) {
    if (!req.endTime().isAfter(req.startTime())) {
      throw new IllegalArgumentException("endTime must be after startTime");
    }

    var doctor = doctorRepository.findByIdForUpdate(req.doctorId())
        .orElseThrow(() -> new IllegalArgumentException("Doctor not found: " + req.doctorId()));

    var patient = patientRepository.findById(req.patientId())
        .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + req.patientId()));

    var overlapping = appointmentRepository.findOverlapping(doctor.getId(), req.startTime(), req.endTime());
    if (!overlapping.isEmpty()) {
      throw new IllegalStateException("Time slot overlaps existing appointment(s) for doctorId=" + doctor.getId());
    }

    Appointment a = new Appointment();
    a.setDoctor(doctor);
    a.setPatient(patient);
    a.setStartTime(req.startTime());
    a.setEndTime(req.endTime());
    a.setStatus(AppointmentStatus.SCHEDULED);

    return appointmentRepository.save(a);
  }

  @Transactional
  public Appointment changeStatus(Long appointmentId, AppointmentStatus newStatus) {
    var a = appointmentRepository.findById(appointmentId)
        .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + appointmentId));

    var current = a.getStatus();

    // Validate state transitions
    boolean allowed = switch (current) {
      case SCHEDULED -> (newStatus == AppointmentStatus.IN_PROGRESS
          || newStatus == AppointmentStatus.CANCELLED
          || newStatus == AppointmentStatus.NO_SHOW);
      case IN_PROGRESS -> (newStatus == AppointmentStatus.COMPLETED);
      case COMPLETED, CANCELLED, NO_SHOW -> false;
    };

    if (!allowed) {
      throw new IllegalStateException("Invalid transition: " + current + " -> " + newStatus);
    }

    a.setStatus(newStatus);
    return a;
  }

  public java.util.List<Appointment> listByDoctorAndRange(Long doctorId, Instant from, Instant to) {
    return appointmentRepository.findByDoctorAndRange(doctorId, from, to);
  }
}
