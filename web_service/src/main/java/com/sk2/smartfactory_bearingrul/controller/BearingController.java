package com.sk2.smartfactory_bearingrul.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sk2.smartfactory_bearingrul.dto.PredictionBearingDto;
import com.sk2.smartfactory_bearingrul.dto.SensorBearingDto;
import com.sk2.smartfactory_bearingrul.service.BearingService;
import com.sk2.smartfactory_bearingrul.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bearing")
public class BearingController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final BearingService bearingService;
    private final NotificationService notificationService;

    @ApiOperation(value = "센서 데이터 조회", notes = "table과 id를 입력 받아 sensor data를 조회합니다.")
    @GetMapping("/sensor/{table}/{id}")
    public ResponseEntity<List<SensorBearingDto>> getSensorData(@PathVariable String table, @PathVariable String id) throws JsonProcessingException {
        if (id.equals("-Infinity"))
            id = "1";

        if (bearingService.existsSensorById(table, Long.parseLong(id))) { // redis에 해당 id에 대한 데이터가 존재하면
            return ResponseEntity.ok().body(bearingService.getSensorListById(table, Long.parseLong(id))); // id ~ id 이후의 데이터 리스트를 반환
        } else { // redis에 해당 id에 대한 데이터가 존재하지 않으면
            String apiUrl = "https://win1.i4624.tk/data/full_test_table_" + table.toLowerCase().replace(" ", "") + "/" + id;
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class); // DB API에 요청을 보내서 id ~ id 이후의 데이터 리스트를 읽어옴
            if (response.getStatusCode() == HttpStatus.OK) { // status가 ok이면
                bearingService.saveSensor(table, response.getBody()); // 데이터를 redis에 저장
                return ResponseEntity.ok().body(bearingService.parsingSensor(response.getBody())); // API response 값을 리스트로 변환하여 return
            } else {  // status가 ok가 아니면
                return ResponseEntity.status(response.getStatusCode()).build(); // DB API reponse의 status를 반환
            }
        }
    }

    @ApiOperation(value = "예측 데이터 조회", notes = "table과 id를 입력 받아 prediction data를 조회합니다.")
    @GetMapping("/prediction/{table}/{id}")
    public ResponseEntity<List<PredictionBearingDto>> getPredictionData(@PathVariable String table, @PathVariable String id) throws JsonProcessingException {
        if (id.equals("-Infinity"))
            id = "1";

        if (bearingService.existsPredictionById(table, Long.parseLong(id))) { // redis에 해당 id에 대한 데이터가 존재하면
            return ResponseEntity.ok().body(bearingService.getPredictionListById(table, Long.parseLong(id))); // id ~ id 이후의 데이터 리스트를 반환
        } else { // redis에 해당 id에 대한 데이터가 존재하지 않으면
            String apiUrl = "https://win1.i4624.tk/output/prediction_full_test_table_" + table.toLowerCase().replace(" ", "") + "/" + id;
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class); // DB API에 요청을 보내서 id ~ id 이후의 데이터 리스트를 읽어옴
            if (response.getStatusCode() == HttpStatus.OK) { // status가 ok이면
                bearingService.savePrediction(table, response.getBody()); // 데이터를 redis에 저장
                notificationService.checkNotification(table, response.getBody());
                return ResponseEntity.ok().body(bearingService.parsingPrediction(response.getBody())); // API response 값을 리스트로 변환하여 return
            } else { // status가 ok가 아니면
                return ResponseEntity.status(response.getStatusCode()).build(); // DB API reponse의 status를 반환
            }
        }
    }
}