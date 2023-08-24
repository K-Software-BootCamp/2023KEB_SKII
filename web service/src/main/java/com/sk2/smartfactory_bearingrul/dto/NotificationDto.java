package com.sk2.smartfactory_bearingrul.dto;
import com.sk2.smartfactory_bearingrul.entity.Notification;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private long notificationId;
    private String publisher;
    private String message;
    private LocalDateTime createdAt;

    public static NotificationDto from(Notification entity) {
        return NotificationDto.builder()
                .notificationId(entity.getNotificationId())
                .publisher(entity.getPublisher())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public Notification toEntity() {
        return Notification.builder()
                .notificationId(notificationId)
                .publisher(publisher)
                .message(message)
                .createdAt(createdAt)
                .build();
    }
}