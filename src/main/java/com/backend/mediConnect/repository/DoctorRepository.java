package com.backend.mediConnect.repository;

import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    @Query("SELECT d FROM Doctor d WHERE d.specialty.name = :specialtyName")
    List<Doctor> findDoctorsBySpecialty(String specialtyName);

    //@Query("SELECT d FROM Doctor d WHERE d.name LIKE %:doctorName% OR d.lastname LIKE %:doctorName%")
    //List<Doctor> findDoctorsByNameOrLastnameContaining(String doctorName);



}
