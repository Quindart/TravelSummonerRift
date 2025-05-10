package vn.edu.iuh.fit.bookingservice.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private  final ObjectMapper  objectMapper;

    public RedisService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public <T> void setValue(String key, Object value, int timeout) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(value);
        this.redisTemplate.opsForValue().set(key,json,timeout, TimeUnit.MINUTES);
    }

    public <T> void setValue(String key, Object value) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(value);
        this.redisTemplate.opsForValue().set(key,json,5, TimeUnit.MINUTES);
    }

    public <T> T getValue(String key, TypeReference<T> typeReference) throws JsonProcessingException {
        String json = (String) this.redisTemplate.opsForValue().get(key);
        if(json == null) return null;
        return objectMapper.readValue(json,typeReference);
    }

}
