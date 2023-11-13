package com.spomprt.weightminder.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortenerResult(
        @JsonProperty("result_url") String resultUrl
) {
}
