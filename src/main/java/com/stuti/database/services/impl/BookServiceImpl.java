package com.stuti.database.services.impl;

import com.stuti.database.domain.entities.AuthorEntity;
import com.stuti.database.domain.entities.BookEntity;
import com.stuti.database.repositories.BookRepository;
import com.stuti.database.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity, String isbn) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(BookEntity bookEntity) {
            Optional<BookEntity> existingBookEntity = bookRepository.findById(bookEntity.getIsbn());
            return existingBookEntity.map(existingEntity -> {
                Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingEntity :: setTitle);
                return bookRepository.save(existingEntity);
            }).orElseThrow(() -> new RuntimeException("AuthorEntity does not exist"));

    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
