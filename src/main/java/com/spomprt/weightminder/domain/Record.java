package com.spomprt.weightminder.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.compare.ComparableUtils;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@Entity
@Table(name = "records")
public class Record {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private Person owner;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public static Record newRecord(Person owner, Double weight) {
        Record record = new Record();
        record.setId(UUID.randomUUID());
        record.setOwner(owner);
        record.setWeight(weight);
        record.setCreatedAt(LocalDate.now());
        return record;
    }

    public boolean isIncludedToLastDays(int days) {
        LocalDate lastTenDays = LocalDate.now().minusDays(days - 1);
        return ComparableUtils.is(this.createdAt).greaterThanOrEqualTo(lastTenDays);
    }

    public boolean isActual() {
        return this.createdAt.isEqual(LocalDate.now());
    }

    public void updateWeight(Double newWeight) {
        this.weight = newWeight;
    }

    public Double currentWeight() {
        return this.weight;
    }

    public LocalDate whenAdded() {
        return this.createdAt;
    }

    public String getPrettyRecord() {
        String date = "Дата записи: " + this.whenAdded();

        String weight = "Вес: " + this.currentWeight();

        return date + ". " + weight;
    }

}
