package com.spomprt.weightminder.external.model;

import java.util.List;

public record Dataset(
        String type,
        String label,
        List<Double> data,
        boolean fill
) {
}
