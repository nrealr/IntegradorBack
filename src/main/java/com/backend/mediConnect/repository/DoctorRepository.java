package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE d.specialty.name = :specialtyName")
    List<Doctor> findDoctorsBySpecialty(@Param("specialtyName") String specialtyName);

    @Query("SELECT DISTINCT d FROM Doctor d " +
            "LEFT JOIN FETCH d.specialty s " +
            "LEFT JOIN FETCH d.features f " +
            "LEFT JOIN d.location l " +
            "WHERE (LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(d.lastname) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND LOWER(l.name) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Doctor> searchDoctors(@Param("query") String query, @Param("location") String location);
}
