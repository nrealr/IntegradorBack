package com.backend.mediConnect.service;

import com.backend.mediConnect.dto.input.DoctorInputDto;
import com.backend.mediConnect.dto.input.FeatureInputDto;
import com.backend.mediConnect.dto.output.DoctorOutputDto;
import com.backend.mediConnect.dto.output.FeatureOutputDto;
import com.backend.mediConnect.dto.update.DoctorUpdateDto;
import com.backend.mediConnect.dto.update.FeatureUpdateDto;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IFeatureService {
    FeatureOutputDto addFeature(FeatureInputDto feature) throws IOException;
    List<FeatureOutputDto> listFeatures();
    FeatureOutputDto findFeatureById(Long id);
    FeatureOutputDto updateFeature(FeatureUpdateDto doctor) throws ResourceNotFoundException, IOException;
    void deleteFeature(Long id) throws ResourceNotFoundException;
}
