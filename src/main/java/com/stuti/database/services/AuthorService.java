package com.stuti.database.services;

import com.stuti.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    public AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findById(Long id);

    boolean isExist(Long id);

    AuthorEntity partialUpdate(AuthorEntity authorEntity);

    void delete(Long id);
}
