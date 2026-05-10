package com.khoa2111.appointmentbooking.patient.api;

import com.khoa2111.appointmentbooking.patient.Patient;
import com.khoa2111.appointmentbooking.patient.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
  private final PatientRepository patientRepository;

  public PatientController(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Patient create(@Valid @RequestBody CreatePatientRequest req) {
    Patient p = new Patient();
    p.setName(req.name());
    p.setPhone(req.phone());
    return patientRepository.save(p);
  }

  @GetMapping
  public List<Patient> list() {
    return patientRepository.findAll();
  }
}
