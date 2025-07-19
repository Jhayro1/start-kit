package com.winkstec.redis;

import com.winkstec.redis.core.RedisKeyPrefix;

public class RedisKeyBuilder {

    public static String build(RedisKeyPrefix prefix, String identifier) {
        return prefix.getPrefix() + ":" + identifier;
    }
}
