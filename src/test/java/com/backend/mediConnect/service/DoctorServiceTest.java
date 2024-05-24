package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class DoctorServiceTest {

    @Autowired
    private IDoctorService doctorService;

    @Test
    public void shouldAddADoctorWithoutFeatures(){
        DoctorInputDto doctor = new DoctorInputDto("Pedro", "Pérez", "6857226-9", "https://images.app.goo.gl/Db6AwF3fVSHsSsWz9", "General Practice doctor");

        DoctorOutputDto doctorAdded = doctorService.registerDoctor(doctor, Collections.emptySet());

        Assertions.assertTrue(doctorAdded.getId() != 0);
    }


    @Test
    public void shouldAddADoctorWithFeatures() {
        DoctorInputDto doctor = new DoctorInputDto("Pedro", "Pérez", "6858226-9", "https://images.app.goo.gl/Db6AwF3fVSHsSsWz9", "General Practice doctor");

        Set<Long> featureIds = new HashSet<>(Arrays.asList(1L, 2L));

        DoctorOutputDto doctorAdded = doctorService.registerDoctor(doctor, featureIds);

        Assertions.assertTrue(doctorAdded.getId() != 0);
    }

    @Test
    public void shouldListAllDoctors(){
        List<DoctorOutputDto> doctorsList = doctorService.listDoctors();
        Assertions.assertFalse(doctorsList.isEmpty());
    }

}
