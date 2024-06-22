package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    // Método para buscar disponibilidades por doctor, fecha y status
    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND DATE(a.startTime) = :date AND a.status.name = :status")
    List<Availability> findByDoctorIdAndDateAndStatus(@Param("doctorId") Long doctorId, @Param("date") LocalDate date, @Param("status") String status);

    // Otros métodos de consulta
    List<Availability> findByDoctorId(Long doctorId);

    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.startTime <= :endTime AND a.endTime >= :startTime")
    List<Availability> findByDoctorIdAndTimeRange(@Param("doctorId") Long doctorId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND DATE(a.startTime) = :date")
    List<Availability> findByDoctorIdAndDate(@Param("doctorId") Long doctorId, @Param("date") LocalDate date);
}

