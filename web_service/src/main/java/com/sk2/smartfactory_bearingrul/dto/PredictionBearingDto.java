package com.sk2.smartfactory_bearingrul.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionBearingDto {
    private long pred_id;
    private String inference_time;
    private double prediction;
    private String timestamp;
}