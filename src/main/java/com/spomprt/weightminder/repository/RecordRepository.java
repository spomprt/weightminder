package com.spomprt.weightminder.repository;

import com.spomprt.weightminder.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {

    @Query(value = """
            select r from Record r
            where r.owner.username = :username
            order by r.createdAt desc
            limit 1
            """)
    Optional<Record> findLatestRecord(@Param("username") String username);

}
