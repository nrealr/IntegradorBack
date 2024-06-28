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
            "WHERE (" +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.lastname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ") AND LOWER(l.name) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Doctor> searchDoctors(@Param("keyword") String keyword, @Param("location") String location);

    // Nuevo método para manejar múltiples palabras clave
    @Query("SELECT DISTINCT d FROM Doctor d " +
            "LEFT JOIN FETCH d.specialty s " +
            "LEFT JOIN FETCH d.features f " +
            "LEFT JOIN d.location l " +
            "WHERE (" +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
            "LOWER(d.lastname) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
            "LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
            "LOWER(d.lastname) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword2, '%')) OR " +
            "LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword2, '%'))" +
            ") AND LOWER(l.name) LIKE LOWER(CONCAT('%', :location, '%'))")
    List<Doctor> searchDoctorsWithMultipleKeywords(@Param("keyword1") String keyword1, @Param("keyword2") String keyword2, @Param("location") String location);
}