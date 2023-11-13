package com.spomprt.weightminder.domain;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PersonTest {

    @Test
    void noData() {
        //arrange
        Person person = new Person();
        List<Record> records = Collections.emptyList();
        ReflectionTestUtils.setField(person, "records", records);

        //act
        String noData = person.getLastTenPretty();

        //assert
        assertThat(noData).isEqualTo("Нет данных.");
    }

    @Test
    void getLastTen() {
        //arrange
        Person person = new Person();
        List<Record> records = List.of(
                Record.newRecord(person, 101.0),
                Record.newRecord(person, 99.0),
                Record.newRecord(person, 86.0)
        );
        ReflectionTestUtils.setField(person, "records", records);

        //act
        List<Record> lastTen = person.getLastTen();

        //assert
        assertThat(lastTen).hasSize(3);
    }

    @Test
    void getLastTen_2() {
        //arrange
        Person person = new Person();
        List<Record> records = List.of(
                Record.newRecord(person, 1.0),
                Record.newRecord(person, 2.0),
                Record.newRecord(person, 3.0),
                Record.newRecord(person, 4.0),
                Record.newRecord(person, 5.0),
                Record.newRecord(person, 6.0),
                Record.newRecord(person, 7.0),
                Record.newRecord(person, 8.0),
                Record.newRecord(person, 9.0),
                Record.newRecord(person, 10.0),
                Record.newRecord(person, 11.0),
                Record.newRecord(person, 12.0)
        );
        ReflectionTestUtils.setField(person, "records", records);

        //act
        List<Record> lastTen = person.getLastTen();

        //assert
        assertThat(lastTen).hasSize(10);
    }

}