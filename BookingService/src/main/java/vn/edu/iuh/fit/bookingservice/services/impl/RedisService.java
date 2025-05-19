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
    private static final String PREFIX = "booking:";

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


    // Lưu booking vào Redis với TTL 15 phút
    public void saveBooking(String bookingId) {
        String key = PREFIX + bookingId;
        redisTemplate.opsForValue().set(key, "PENDING", 20, TimeUnit.MINUTES);
    }

    // Kiểm tra TTL còn lại
    public long getTTL(String bookingId) {
        String key = PREFIX + bookingId;
        Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return ttl != null ? ttl : -2;  // -2 là không tồn tại
    }

    // Kiểm tra key còn tồn tại
    public boolean isBookingExist(String bookingId) {
        String key = PREFIX + bookingId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
