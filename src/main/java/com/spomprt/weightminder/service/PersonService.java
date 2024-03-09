package com.spomprt.weightminder.service;

import com.spomprt.weightminder.cache.UrlShortenerLinksCache;
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
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    private final UrlShortenerLinksCache urlShortenerLinksCache;

    @Transactional
    public void register(Long userId, Long chatId) {
        Optional<Person> personMaybe = personRepository.findById(userId);

        if (personMaybe.isEmpty()) {
            Person person = Person.newPerson(userId, chatId);

            entityManager.persist(person);

            log.debug("{} is registered.", person);
        }
    }

    @Transactional
    public void addRecord(Long userId, Double weight) {
        Person person = this.get(userId);

        boolean isNewRecord = person.addRecord(weight);

        if (isNewRecord) {
            urlShortenerLinksCache.delete(userId);
        }

        log.debug("Record with weight {} to {} added", weight, person);
    }

    public Stream<Person> getAll() {
        return personRepository.findPersons();
    }


    public Person get(Long userId) {
        return personRepository.findPerson(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

}
