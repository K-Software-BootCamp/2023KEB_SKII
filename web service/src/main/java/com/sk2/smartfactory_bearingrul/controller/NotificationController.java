package com.sk2.smartfactory_bearingrul.controller;

import com.sk2.smartfactory_bearingrul.dto.NotificationDto;
import com.sk2.smartfactory_bearingrul.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/notification")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @ApiOperation(value = "알림 목록 조회", notes = "테이블의 모든 알림 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @ApiOperation(value = "새로운 알림 개수 조회", notes = "입력 받은 시간 후에 생성된 알림의 개수를 조회합니다.")
    @GetMapping("/countAfter/{createdAt}")
    public ResponseEntity<Long> getCountByCreatedAt(@PathVariable String createdAt) {
        return ResponseEntity.ok(notificationService.getCountNewNoti(createdAt));
    }
}