package com.spomprt.weightminder.repository;

import com.spomprt.weightminder.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("""
            select p from Person p left join fetch p.records
            where p.userId = :userId
            """)
    Optional<Person> findPerson(@Param("userId") Long userId);

    @Query("""
            select p from Person p
            """)
    Stream<Person> findPersons();

}
