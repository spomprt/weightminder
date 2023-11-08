package com.spomprt.weightminder.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
    private Set<Record> records = new HashSet<>();

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

    //todo добавить методы первый вес и последний вес

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

}
