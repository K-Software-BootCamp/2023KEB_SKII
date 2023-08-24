package com.sk2.smartfactory_bearingrul.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk2.smartfactory_bearingrul.dto.SensorBearingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SensorBearingRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String serialize(SensorBearingDto sensor) throws JsonProcessingException {
        return objectMapper.writeValueAsString(sensor);
    }

    private SensorBearingDto deserialize(String value) throws JsonProcessingException {
        if (value == null) return null;
        return objectMapper.readValue(value, SensorBearingDto.class);
    }

    private String generateKey(String table, Long id) {
        return "SensorBearing:" + table + ":" + id;
    }

    public void save(String table, SensorBearingDto dto) {
        try {
            redisTemplate.opsForValue().set(generateKey(table, dto.getId()), serialize(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(String table, Long id) {
        if (redisTemplate.opsForValue().get(generateKey(table, id)) != null)
            return true;
        return false;
    }

    public SensorBearingDto findById(String table, Long id) {
        try {
            return deserialize((String) redisTemplate.opsForValue().get(generateKey(table, id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SensorBearingDto> getListById(String table, Long id) {
        String pattern = "SensorBearing:" + table + ":*";
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys != null) {
            return keys.stream()
                    .filter(key -> {
                        String[] parts = key.split(":");
                        if (parts.length >= 3) {
                            return Long.parseLong(parts[2]) >= id;
                        }
                        return false;
                    })
                    .map(key -> {
                        try {
                            return deserialize((String) redisTemplate.opsForValue().get(key));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .sorted(Comparator.comparingLong(dto -> dto.getId()))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
