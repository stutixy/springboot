package com.stuti.database.dao;

import com.stuti.database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(long id);

    List<Author> find();

    void update(long l, Author author);

    void delete(long id);
}
