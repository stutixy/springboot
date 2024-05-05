package com.stuti.database.services;

import com.stuti.database.domain.entities.BookEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BookService {

    public BookEntity createBook(BookEntity bookEntity, String isbn);

    List<BookEntity> findAll();

    Optional<BookEntity> findById(String isbn);

    boolean isExist(String isbn);

    BookEntity partialUpdate(BookEntity bookEntity);

    void delete(String isbn);
}
