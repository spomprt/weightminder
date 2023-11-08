package com.spomprt.weightminder.service;

import com.spomprt.weightminder.domain.Person;
import com.spomprt.weightminder.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personRepository;

    @Transactional
    public void register(String username) {
        Optional<Person> personMaybe = personRepository.findById(username);

        if (personMaybe.isPresent()) {
            log.info("Person with username {} already exist.", username);
        } else {
            Person person = Person.newPerson(username);

            entityManager.persist(person);

            log.info("{} is registered.", person);
        }
    }

    @Transactional
    public void addRecord(String username, Double weight) {
        Person person = this.get(username);

        person.addRecord(weight);

        log.info("Record with weight {} to {} added", weight, person);
    }

    public Person get(String username) {
        return personRepository.findPerson(username)
                .orElseThrow(EntityNotFoundException::new);
    }

}
