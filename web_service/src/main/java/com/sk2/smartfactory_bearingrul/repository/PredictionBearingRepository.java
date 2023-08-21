package com.sk2.smartfactory_bearingrul.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk2.smartfactory_bearingrul.dto.PredictionBearingDto;
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
public class PredictionBearingRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String serialize(PredictionBearingDto pred) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pred);
    }

    private PredictionBearingDto deserialize(String value) throws JsonProcessingException {
        if (value == null) return null;
        return objectMapper.readValue(value, PredictionBearingDto.class);
    }

    private String generateKey(String table, Long id) {
        return "PredictionBearing:" + table + ":" + id;
    }

    public void save(String table, PredictionBearingDto dto) {
        try {
            redisTemplate.opsForValue().set(generateKey(table, dto.getPred_id()), serialize(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsById(String table, Long id) {
        if (redisTemplate.opsForValue().get(generateKey(table, id)) != null)
            return true;
        return false;
    }

    public PredictionBearingDto findById(String table, Long id) {
        try {
            return deserialize((String) redisTemplate.opsForValue().get(generateKey(table, id)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PredictionBearingDto> getListById(String table, Long id) {
        String pattern = "PredictionBearing:" + table + ":*";
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
                    .sorted(Comparator.comparingLong(dto -> dto.getPred_id()))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
