package com.sk2.smartfactory_bearingrul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk2.smartfactory_bearingrul.dto.PredictionBearingDto;
import com.sk2.smartfactory_bearingrul.dto.SensorBearingDto;
import com.sk2.smartfactory_bearingrul.repository.PredictionBearingRepository;
import com.sk2.smartfactory_bearingrul.repository.SensorBearingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BearingService {

    private final SensorBearingRepository sensorBearingRepository;
    private final PredictionBearingRepository predictionBearingRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<SensorBearingDto> parsingSensor(String data) throws JsonProcessingException {
        return objectMapper.readValue(data, new TypeReference<List<SensorBearingDto>>() {
                }).stream()
                .sorted(Comparator.comparingLong(dto -> dto.getId()))
                .collect(Collectors.toList());
    }

    public List<PredictionBearingDto> parsingPrediction(String data) throws JsonProcessingException {
        return objectMapper.readValue(data, new TypeReference<List<PredictionBearingDto>>() {
                }).stream()
                .sorted(Comparator.comparingLong(dto -> dto.getPred_id()))
                .collect(Collectors.toList());
    }

    public boolean existsSensorById(String table, Long id) {
        return sensorBearingRepository.existsById(table, id);
    }

    public boolean existsPredictionById(String table, Long id) {
        return predictionBearingRepository.existsById(table, id);
    }

    public List<SensorBearingDto> getSensorListById(String table, Long id) {
        return sensorBearingRepository.getListById(table, id);
    }

    public List<PredictionBearingDto> getPredictionListById(String table, Long id) {
        return predictionBearingRepository.getListById(table, id);
    }


    public void saveSensor(String table, String data) throws JsonProcessingException {
        List<SensorBearingDto> sensorDataList = parsingSensor(data); // 리스트로 변환
        sensorDataList.forEach(sensorData -> sensorBearingRepository.save(table, sensorData)); // 각 dto를 redis에 저장
    }

    public void savePrediction(String table, String data) throws JsonProcessingException {
        List<PredictionBearingDto> predictionDataList = parsingPrediction(data); // 리스트로 변환
        predictionDataList.forEach(predictionData -> predictionBearingRepository.save(table, predictionData)); // 각 dto를 redis에 저장
    }
}