package com.spomprt.weightminder.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@Entity
@Table(name = "persons")
public class Person {

    @ToString.Include
    @Id
    private String username;

    @ToString.Exclude
    @OneToMany(
            mappedBy = "owner",
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            orphanRemoval = true
    )
    private List<Record> records = new ArrayList<>();

    public static Person newPerson(String username) {
        Person person = new Person();
        person.setUsername(username);
        return person;
    }

    public void addRecord(Double weight) {
        Optional<Record> actualRecordMaybe = this.getActualRecord();

        if (actualRecordMaybe.isEmpty()) {
            Record record = Record.newRecord(this, weight);
            records.add(record);
        } else {
            Record record = actualRecordMaybe.get();
            record.updateWeight(weight);
        }
    }

    public Set<Record> getRecordsForLastTenDays() {
        return this.getRecords().stream()
                .filter(r -> r.isIncludedToLastDays(10))
                .collect(Collectors.toSet());
    }

    public Optional<Record> getActualRecord() {
        return this.records.stream()
                .filter(Record::isActual)
                .findFirst();
    }

    public String getInitial() {
        Record record = this.records.get(0);

        return record.getPrettyRecord();
    }

    public String getCurrent() {
        Record record = this.records.get(records.size() - 1);

        return record.getPrettyRecord();
    }

}
