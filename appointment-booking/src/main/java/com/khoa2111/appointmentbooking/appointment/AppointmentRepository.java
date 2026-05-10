package com.khoa2111.appointmentbooking.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  // Tìm lịch overlap (trừ CANCELLED). Rule: newStart < existingEnd AND newEnd > existingStart
  @Query("""
      select a from Appointment a
      where a.doctor.id = :doctorId
        and a.status <> com.khoa2111.appointmentbooking.appointment.AppointmentStatus.CANCELLED
        and :start < a.endTime
        and :end > a.startTime
      """)
  List<Appointment> findOverlapping(
      @Param("doctorId") Long doctorId,
      @Param("start") Instant start,
      @Param("end") Instant end
  );

  @Query("""
      select a from Appointment a
      where a.doctor.id = :doctorId
        and a.startTime >= :from
        and a.endTime <= :to
      order by a.startTime asc
      """)
  List<Appointment> findByDoctorAndRange(
      @Param("doctorId") Long doctorId,
      @Param("from") Instant from,
      @Param("to") Instant to
  );
}
