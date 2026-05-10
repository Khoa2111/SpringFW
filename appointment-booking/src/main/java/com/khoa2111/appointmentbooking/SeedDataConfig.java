package com.khoa2111.appointmentbooking;

import com.khoa2111.appointmentbooking.doctor.Doctor;
import com.khoa2111.appointmentbooking.doctor.DoctorRepository;
import com.khoa2111.appointmentbooking.patient.Patient;
import com.khoa2111.appointmentbooking.patient.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

  @Bean
  CommandLineRunner seed(DoctorRepository doctorRepository, PatientRepository patientRepository) {
    return args -> {
      if (doctorRepository.count() == 0) {
        Doctor d = new Doctor();
        d.setName("Dr. House");
        d.setSpecialty("Diagnostics");
        doctorRepository.save(d);
      }

      if (patientRepository.count() == 0) {
        Patient p = new Patient();
        p.setName("Nguyen Van A");
        p.setPhone("0900000000");
        patientRepository.save(p);
      }
    };
  }
}
