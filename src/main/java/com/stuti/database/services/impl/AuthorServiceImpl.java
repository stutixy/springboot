package com.stuti.database.services.impl;

import com.stuti.database.domain.dto.AuthorDto;
import com.stuti.database.domain.entities.AuthorEntity;
import com.stuti.database.repositories.AuthorRepository;
import com.stuti.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(AuthorEntity authorEntity) {
        Optional<AuthorEntity> existingAuthorEntity = authorRepository.findById(authorEntity.getId());
        return existingAuthorEntity.map(existingEntity -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingEntity :: setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingEntity :: setAge);
            return authorRepository.save(existingEntity);
        }).orElseThrow(() -> new RuntimeException("AuthorEntity does not exist"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
