package com.sk2.smartfactory_bearingrul.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorBearingDto {
    private long id;
    private int hour;
    private int minutes;
    private int second;
    private int microsecond;
    private double horiz_accel;
    private double vert_accel;
    private String csv_number;
}