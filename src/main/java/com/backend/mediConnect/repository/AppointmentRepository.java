package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctorId = :doctorId AND " +
            "(:endTime > a.startTime AND :startTime < a.endTime)")
    List<Appointment> findByDoctorIdAndTimeRange(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
}
