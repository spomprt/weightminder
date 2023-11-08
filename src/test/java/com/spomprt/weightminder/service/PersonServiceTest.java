package com.spomprt.weightminder.service;

import com.spomprt.weightminder.AbstractSpringBootTest;
import com.spomprt.weightminder.domain.Record;
import com.spomprt.weightminder.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonServiceTest extends AbstractSpringBootTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private RecordRepository recordRepository;

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
        Optional<Record> latestRecord = recordRepository.findLatestRecord(username);
        assertTrue(latestRecord.isPresent());
        assertThat(latestRecord.get())
                .extracting(Record::currentWeight)
                .isEqualTo(weight);
    }

}