package com.stuti.database;

import com.stuti.database.domain.Author;
import com.stuti.database.domain.Book;

public final class TestDataUtils {
    public static Author createTestAuthor(long id, String name, int age) {
        return Author.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static Book createTestBook(String isbn, String title, long authorId) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(authorId)
                .build();
    }
}
