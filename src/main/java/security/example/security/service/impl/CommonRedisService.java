package security.example.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import security.example.security.service.RedisService;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Set;

@Service
public class CommonRedisService<T> implements RedisService<T> {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public CommonRedisService(ObjectMapper redisObjectMapper, RedisTemplate<String, Object> redisTemplate) {
        this.objectMapper = redisObjectMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<T> searchList(String key) throws Exception {
        String json = (String) redisTemplate.opsForValue().get(key);
        return json != null ?
                objectMapper.readValue(json, new TypeReference<List<T>>() {
                }) : null;
    }

    @Override
    public void saveList(String key, List<T> list) throws Exception {
        String json = objectMapper.writeValueAsString(list);
        redisTemplate.opsForValue().set(key, json);
    }

    @Override
    public void deleteKeysStartingWith(String prefix) {
        Set<String> keys = redisTemplate.keys(prefix + "*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
    }

}
