package com.sk2.smartfactory_bearingrul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sk2.smartfactory_bearingrul.dto.NotificationDto;
import com.sk2.smartfactory_bearingrul.dto.PredictionBearingDto;
import com.sk2.smartfactory_bearingrul.entity.Notification;
import com.sk2.smartfactory_bearingrul.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final BearingService bearingService;

    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(NotificationDto::from)
                .collect(Collectors.toList());
    }

    public void checkNotification(String table, String data) throws JsonProcessingException {
        List<PredictionBearingDto> predictionDataList = bearingService.parsingPrediction(data);

        // 알림을 생성할 리스트
        List<NotificationDto> notifications = new ArrayList<>();

        predictionDataList.stream().filter(p -> p.getPrediction() >= 0.9)
                .forEach(p -> {
                    NotificationDto dto = NotificationDto.builder()
                            .publisher(table)
                            .message("고장날 확률이 " + p.getPrediction() + " 이상으로, 주의가 필요합니다.")
                            .build();
                    notifications.add(dto);
                });

        if (!notifications.isEmpty()) {
            saveNotifications(notifications);
        }
    }

    private void saveNotifications(List<NotificationDto> notifications) {
        notifications.forEach(notification -> {
            Notification entity = notification.toEntity();
            notificationRepository.save(entity);
        });
    }


    public long getCountNewNoti(String createdAt) {
        return notificationRepository.countByCreatedAtAfter(LocalDateTime.parse(createdAt.replace("Z", "")));
    }
}