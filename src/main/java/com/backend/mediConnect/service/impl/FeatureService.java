package com.backend.mediConnect.service.impl;

import com.backend.mediConnect.dto.input.FeatureInputDto;
import com.backend.mediConnect.dto.output.FeatureOutputDto;
import com.backend.mediConnect.dto.update.FeatureUpdateDto;
import com.backend.mediConnect.entity.Feature;
import com.backend.mediConnect.exceptions.ResourceNotFoundException;
import com.backend.mediConnect.repository.FeatureRepository;
import com.backend.mediConnect.service.IFeatureService;
import com.backend.mediConnect.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService implements IFeatureService {

    private final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);
    private final FeatureRepository featureRepository;
    private final ModelMapper modelMapper;
    private final JsonPrinter jsonPrinter;

    public FeatureService(FeatureRepository featureRepository, ModelMapper modelMapper, JsonPrinter jsonPrinter) {
        this.featureRepository = featureRepository;
        this.modelMapper = modelMapper;
        this.jsonPrinter = jsonPrinter;
    }

    @Override
    public FeatureOutputDto addFeature(FeatureInputDto feature) {
        LOGGER.info("Feature information received " + jsonPrinter.toString(feature));
        Feature featureEntity = modelMapper.map(feature, Feature.class);

        Feature featurePersisted = featureRepository.save(featureEntity);

        FeatureOutputDto featureOutputDto = modelMapper.map(featurePersisted, FeatureOutputDto.class);

        LOGGER.info("Feature saved: " + jsonPrinter.toString(featureOutputDto));

        return featureOutputDto;
    }

    @Override
    public List<FeatureOutputDto> listFeatures() {
        List<FeatureOutputDto> features = featureRepository.findAll()
                .stream()
                .map(feature -> modelMapper.map(feature, FeatureOutputDto.class))
                .toList();

        LOGGER.info("List of all features; {}", jsonPrinter.toString((features)));

        return features;
    }

    @Override
    public FeatureOutputDto findFeatureById(Long id) {
        Feature featureSearched = featureRepository.findById(id).orElse(null);
        FeatureOutputDto featureFound = null;

        if (featureSearched != null){
            featureFound = modelMapper.map(featureSearched, FeatureOutputDto.class);
            LOGGER.info("Feature found: {}", jsonPrinter.toString(featureFound));
        } else {
            LOGGER.error("The id is not registered on the database.");
        }
        return featureFound;
    }

    @Override
    public FeatureOutputDto updateFeature(FeatureUpdateDto feature) {
        Feature featureReceived = modelMapper.map(feature, Feature.class);
        Feature featureToModify = featureRepository.findById(featureReceived.getId()).orElse(null);

        FeatureOutputDto featureOutputDto = null;

        if(featureToModify != null){
            featureToModify = featureReceived;
            featureRepository.save(featureToModify);

            featureOutputDto = modelMapper.map(featureToModify, FeatureOutputDto.class);
            LOGGER.warn("Feature updated: {}", jsonPrinter.toString(featureOutputDto));
        } else {
            LOGGER.error("The feature was not found in the database. Update failed.");

        }

        return featureOutputDto;
    }

    @Override
    public void deleteFeature(Long id) throws ResourceNotFoundException {
        if (featureRepository.findById(id).orElse(null) != null){
            featureRepository.deleteById(id);
            LOGGER.warn("Feature with id {} was deleted", id);
        }else {
            LOGGER.error("Couldn't find feature with id {}", id);
            throw new ResourceNotFoundException("Couldn't find feature with id " + id);
        }
    }
}
