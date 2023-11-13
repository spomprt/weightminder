package com.spomprt.weightminder.service;

import com.spomprt.weightminder.AbstractSpringBootTest;
import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.domain.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonServiceTest extends AbstractSpringBootTest {

    @Autowired
    private PersonService personService;

    @Test
    void registerNewPerson() {
        //arrange
        Long userId = 1L;
        Long chatId = 1L;

        //act
        personService.register(userId, chatId);

        //assert
        assertDoesNotThrow(() -> personService.get(userId));
    }

    @Test
    void addWeightToPerson() {
        //arrange
        Long userId = 1L;
        Long chatId = 1L;
        personService.register(userId, chatId);
        Double weight = 100.0;

        //act
        personService.addRecord(userId, weight);

        //assert
        Person person = personService.get(userId);
        assertTrue(person.getActualRecord().isPresent());
        assertThat(person.getActualRecord().get())
                .extracting(Record::currentWeight)
                .isEqualTo(weight);
    }

    @Test
    void updateWeightToPerson() {
        //arrange
        Long userId = 1L;
        Long chatId = 1L;
        personService.register(userId, chatId);
        Double weight = 100.0;
        Double newWeight = 101.0;

        //act
        personService.addRecord(userId, weight);
        personService.addRecord(userId, newWeight);

        //assert
        List<Record> withUpdated = personService.get(userId)
                .getLastTen();

        //assert
        assertThat(withUpdated).hasSize(1);
    }

}