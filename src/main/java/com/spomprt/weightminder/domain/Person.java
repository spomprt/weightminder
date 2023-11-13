package com.spomprt.weightminder.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@Entity
@Table(name = "persons")
public class Person {

    private static final String NO_DATA = "Нет данных.";

    @ToString.Include
    @Id
    private Long userId;

    @Column(name = "chat_id")
    private Long chatId;

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

    public static Person newPerson(Long userId, Long chatId) {
        Person person = new Person();
        person.setUserId(userId);
        person.setChatId(chatId);
        return person;
    }

    public boolean addRecord(Double weight) {
        boolean isNewRecord = true;

        Optional<Record> actualRecordMaybe = this.getActualRecord();

        if (actualRecordMaybe.isEmpty()) {
            Record record = Record.newRecord(this, weight);
            records.add(record);
        } else {
            Record record = actualRecordMaybe.get();

            isNewRecord = record.updateWeight(weight);
        }

        return isNewRecord;
    }

    public Optional<Record> getActualRecord() {
        return this.records.stream()
                .filter(Record::isActual)
                .findFirst();
    }

    public String getInitialPretty() {
        return this.getInitial()
                .map(Record::getPrettyRecord)
                .orElse(NO_DATA);
    }

    public String getCurrentPretty() {
        return this.getCurrent()
                .map(Record::getPrettyRecord)
                .orElse(NO_DATA);
    }

    public String getLastTenPretty() {
        List<Record> lastTen = this.getLastTen();

        if (lastTen.isEmpty()) {
            return NO_DATA;
        }

        return lastTen.stream()
                .map(Record::getPrettyRecord)
                .collect(Collectors.joining("\n\n"));
    }

    public Optional<Record> getInitial() {
        if (this.records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.records.get(0));
    }

    public Optional<Record> getCurrent() {
        if (this.records.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.records.get(records.size() - 1));
    }

    public List<Record> getLastN(int n) {
        if (this.records.isEmpty()) {
            return new ArrayList<>();
        }
        return this.records.subList(Math.max(records.size() - n, 0), records.size());
    }

    public List<Record> getLastTen() {
        if (this.records.isEmpty()) {
            return new ArrayList<>();
        }
        return this.records.subList(Math.max(records.size() - 10, 0), records.size());
    }

    public boolean isNoRecords() {
        return this.records.isEmpty();
    }

    public int getRecordsCount() {
        return this.records.size();
    }

    public Long getChat() {
        return this.chatId;
    }

}
