package com.spomprt.weightminder.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString(
        of = {"username"}
)
@Entity
@Table(name = "persons")
public class Person {

    @Id
    private String username;

    private Double weight;

    protected Person() {
    }

    public static Person newPerson(String username) {
        Person person = new Person();
        person.username = username;
        return person;
    }

}
