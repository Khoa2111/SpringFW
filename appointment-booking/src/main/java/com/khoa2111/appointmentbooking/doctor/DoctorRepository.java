package com.khoa2111.appointmentbooking.doctor;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

  // Lock hàng doctor để serialize các request đặt lịch cho cùng doctor.
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select d from Doctor d where d.id = :id")
  Optional<Doctor> findByIdForUpdate(@Param("id") Long id);
}
