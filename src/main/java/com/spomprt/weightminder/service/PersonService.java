package com.spomprt.weightminder.service;

import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public void register(String username) {
        Person person = Person.newPerson(username);

        personRepository.save(person);

        log.info("{} is registered.", person);
    }

    @Transactional
    public void addRecord(String username, Double weight) {
        Person person = this.get(username);

        person.addRecord(weight);

        log.info("Record with weight {} to {} added", weight, person);
    }

    public Person get(String username) {
        return personRepository.findById(username)
                .orElseThrow(EntityNotFoundException::new);
    }

}
