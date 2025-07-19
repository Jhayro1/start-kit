package com.winkstec.redis;

import java.time.Duration;

public interface RedisService {
    <T> void save(String key, T value, Duration ttl);
    <T> T get(String key, Class<T> clazz);
    void delete(String key);
}
