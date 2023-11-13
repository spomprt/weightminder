package com.spomprt.weightminder.service.mapper;

import com.spomprt.weightminder.domain.Record;
import com.spomprt.weightminder.external.model.*;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ChartMapper {

    private ChartMapper() {
        throw new UnsupportedOperationException();
    }

    public static Chart map(List<Record> records) {
        Pair<Double, Double> minMax = getMinMax(records);

        Options options = new Options(
                new Scales(
                        Collections.singletonList(
                                new Axes(
                                        true,
                                        "left",
                                        new Ticks(
                                                minMax.getLeft(),
                                                minMax.getRight(),
                                                0.5
                                        )
                                )
                        )
                )
        );

        Content content = new Content(
                records.stream()
                        .map(Record::whenAdded)
                        .map(LocalDate::toString)
                        .toList(),
                Collections.singletonList(
                        new Dataset(
                                "line",
                                "Динамика",
                                records.stream()
                                        .map(Record::currentWeight)
                                        .collect(Collectors.toList()),
                                false
                        )
                )
        );

        return new Chart(
                "line",
                content,
                options
        );
    }

    private static Pair<Double, Double> getMinMax(List<Record> records) {
        if (records.size() == 1) {
            Double minMax = records.get(0).currentWeight();
            return Pair.of(minMax, minMax);
        }

        List<Double> weights = records.stream()
                .sorted(Comparator.comparing(Record::currentWeight))
                .map(Record::currentWeight)
                .toList();

        return Pair.of(weights.get(0), weights.get(weights.size() - 1));
    }

}
