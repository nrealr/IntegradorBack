package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
