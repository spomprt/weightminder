package com.spomprt.weightminder.service;

import com.spomprt.weightminder.AbstractSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PersonServiceTest extends AbstractSpringBootTest {

    @Autowired
    private PersonService personService;

    @Test
    void registerNewUser() {
        //arrange
        String username = "spomprt";

        //act
        personService.register(username);

        //assert
        assertDoesNotThrow(() -> personService.get(username));
    }

}