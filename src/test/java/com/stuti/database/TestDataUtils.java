package com.stuti.database;

import com.stuti.database.domain.dto.AuthorDto;
import com.stuti.database.domain.dto.BookDto;
import com.stuti.database.domain.entities.AuthorEntity;
import com.stuti.database.domain.entities.BookEntity;

public final class TestDataUtils {
    public static AuthorEntity createTestAuthor(Long id, String name, Integer age) {
        return AuthorEntity.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static BookEntity createTestBook(String isbn, String title, AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn(isbn)
                .title(title)
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDto(String isbn, String title, AuthorDto author) {
        return BookDto.builder()
                .isbn(isbn)
                .title(title)
                .authorDto(author)
                .build();
    }
}
