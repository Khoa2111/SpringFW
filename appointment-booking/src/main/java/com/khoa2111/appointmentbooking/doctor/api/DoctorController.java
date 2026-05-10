package com.khoa2111.appointmentbooking.doctor.api;

import com.khoa2111.appointmentbooking.doctor.Doctor;
import com.khoa2111.appointmentbooking.doctor.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
  private final DoctorRepository doctorRepository;

  public DoctorController(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Doctor create(@Valid @RequestBody CreateDoctorRequest req) {
    Doctor d = new Doctor();
    d.setName(req.name());
    d.setSpecialty(req.specialty());
    return doctorRepository.save(d);
  }

  @GetMapping
  public List<Doctor> list() {
    return doctorRepository.findAll();
  }
}
