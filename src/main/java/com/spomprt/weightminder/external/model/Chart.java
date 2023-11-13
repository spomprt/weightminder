package com.spomprt.weightminder.external.model;


public record Chart(
        String type,
        Content data,
        Options options
) {
}
