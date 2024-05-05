package com.stuti.database.repositories;

import com.stuti.database.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("from AuthorEntity where age > ?1")
    Iterable<AuthorEntity> findAuthorWithAgeGreaterThan(int age);
}
