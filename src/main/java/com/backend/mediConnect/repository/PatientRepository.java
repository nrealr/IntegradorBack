package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Patient;
import com.backend.mediConnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUserId(Long userId);

}
