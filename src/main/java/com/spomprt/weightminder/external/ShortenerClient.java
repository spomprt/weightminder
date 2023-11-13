package com.spomprt.weightminder.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "shortener-client", url = "${external.shortener.url}")
public interface ShortenerClient {

    @PostMapping
    ShortenerResult getShortLink(@RequestBody ShortenerRequest request);

}
