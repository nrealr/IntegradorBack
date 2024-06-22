package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.startTime <= :endTime AND a.endTime >= :startTime")
    List<Availability> findByDoctorIdAndTimeRange(@Param("doctorId") Long doctorId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    List<Availability> findByDoctorId(Long doctorId);
}

