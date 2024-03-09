package com.spomprt.weightminder.repository;

import com.spomprt.weightminder.domain.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
}
