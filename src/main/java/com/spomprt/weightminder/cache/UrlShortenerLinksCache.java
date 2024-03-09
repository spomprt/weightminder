package com.spomprt.weightminder.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlShortenerLinksCache {

    private static final String KEY = "ShortUrl";
    private final HashOperations<String, Object, Object> hashOperations;

    public UrlShortenerLinksCache(RedisTemplate<String, String> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void put(Long userId, String url) {
        log.debug("Put user's {} short url to cache", userId);
        hashOperations.put(KEY, userId, url);
    }

    public void delete(Long userId) {
        log.debug("Delete user's {} short url from cache", userId);
        hashOperations.delete(KEY, userId);
    }

    public String get(Long userId) {
        Object cachedShortUrl = hashOperations.get(KEY, userId);

        if (cachedShortUrl == null) {
            log.debug("User's {} short url in cache not found", userId);
            return null;
        } else {
            log.debug("Retrieve user's {} short url from cache", userId);
            return (String) cachedShortUrl;
        }
    }

}
