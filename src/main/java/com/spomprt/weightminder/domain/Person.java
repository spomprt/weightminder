package com.spomprt.weightminder.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private Set<Record> records = new HashSet<>();

    public static Person newPerson(String username) {
        Person person = new Person();
        person.setUsername(username);
        return person;
    }

    public void addRecord(Double weight) {
        Record record = Record.newRecord(this, weight);
        records.add(record);
    }

}
