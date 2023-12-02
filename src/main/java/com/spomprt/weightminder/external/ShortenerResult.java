package com.spomprt.weightminder.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortenerResult(
        @JsonProperty("shortUrl") String shortUrl
) {
}
