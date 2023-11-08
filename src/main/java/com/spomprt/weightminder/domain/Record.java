package com.spomprt.weightminder.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PRIVATE)
@Entity
@Table(name = "records")
public class Record {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private Person owner;

    @EqualsAndHashCode.Exclude
    @Column(name = "weight")
    private Double weight;

    @EqualsAndHashCode.Exclude
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

    public Double currentWeight() {
        return this.weight;
    }

}
