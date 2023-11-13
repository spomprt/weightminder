package com.spomprt.weightminder.external.model;

import java.util.List;

public record Content(
        List<String> labels,
        List<Dataset> datasets
) {
}
