package com.spomprt.weightminder.service;

import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void register(String username) {
        Person person = Person.newPerson(username);

        personRepository.save(person);

        log.info("Person {} is registered.", person);
    }

    public Person get(String username) {
        return personRepository.findById(username)
                .orElseThrow(EntityNotFoundException::new);
    }

}
