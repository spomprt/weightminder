package com.spomprt.weightminder.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortenerRequest(
        @JsonProperty("originalUrl") String originalUrl
) {
}
