package com.spomprt.weightminder.service;

import com.spomprt.weightminder.AbstractSpringBootTest;
import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.domain.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonServiceTest extends AbstractSpringBootTest {

    @Autowired
    private PersonService personService;

    @Test
    void registerNewPerson() {
        //arrange
        String username = "spomprt";

        //act
        personService.register(username);

        //assert
        assertDoesNotThrow(() -> personService.get(username));
    }

    @Test
    void addWeightToPerson() {
        //arrange
        String username = "spomprt";
        personService.register(username);
        Double weight = 100.0;

        //act
        personService.addRecord(username, weight);

        //assert
        Person person = personService.get(username);
        assertTrue(person.getActualRecord().isPresent());
        assertThat(person.getActualRecord().get())
                .extracting(Record::currentWeight)
                .isEqualTo(weight);
    }

    @Test
    void updateWeightToPerson() {
        //arrange
        String username = "spomprt";
        personService.register(username);
        Double weight = 100.0;
        Double newWeight = 101.0;

        //act
        personService.addRecord(username, weight);
        personService.addRecord(username, newWeight);

        //assert
        Person person = personService.get(username);
        assertThat(person.getRecordsForLastTenDays())
                .hasSize(1)
                .first()
                .extracting(Record::currentWeight)
                .isEqualTo(newWeight);
    }

}