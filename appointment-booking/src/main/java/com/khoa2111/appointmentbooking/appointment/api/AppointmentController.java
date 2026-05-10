package com.khoa2111.appointmentbooking.appointment.api;

import com.khoa2111.appointmentbooking.appointment.AppointmentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AppointmentResponse create(@Valid @RequestBody CreateAppointmentRequest req) {
    var a = appointmentService.create(req);
    return AppointmentMapper.toResponse(a);
  }

  @PatchMapping("/{id}/status")
  public AppointmentResponse changeStatus(@PathVariable("id") Long id,
                                         @Valid @RequestBody ChangeStatusRequest req) {
    var a = appointmentService.changeStatus(id, req.status());
    return AppointmentMapper.toResponse(a);
  }

  @GetMapping
  public List<AppointmentResponse> list(
      @RequestParam("doctorId") @NotNull Long doctorId,
      @RequestParam("from") @NotNull Instant from,
      @RequestParam("to") @NotNull Instant to
  ) {
    return appointmentService.listByDoctorAndRange(doctorId, from, to)
        .stream().map(AppointmentMapper::toResponse).toList();
  }
}
