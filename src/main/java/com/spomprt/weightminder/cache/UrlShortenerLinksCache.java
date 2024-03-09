package com.spomprt.weightminder.cache;

import com.spomprt.weightminder.domain.Url;
import com.spomprt.weightminder.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UrlShortenerLinksCache {

    private final UrlRepository urlRepository;

    public UrlShortenerLinksCache(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void put(Long userId, String url) {
        log.debug("Put user's {} short url to cache", userId);
        Url urlEntity = new Url();
        urlEntity.setId(userId);
        urlEntity.setUrl(url);
        urlRepository.save(urlEntity);
    }

    public void delete(Long userId) {
        log.debug("Delete user's {} short url from cache", userId);
        urlRepository.deleteById(userId);
    }

    public String get(Long userId) {
        Optional<Url> urlMaybe = urlRepository.findById(userId);
        if (urlMaybe.isEmpty()) {
            log.debug("User's {} short url in cache not found", userId);
            return null;
        } else {
            log.debug("Retrieve user's {} short url from cache", userId);
            return urlMaybe.get().getUrl();
        }
    }

}
