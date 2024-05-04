package com.stuti.database.dao.impl;

import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testCreateBookGeneratesCorrectSQL(){
        Book book = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", 1L);

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("979-1-3892-8990-0"), eq("The Shining"), eq(1L)
        );
    }

    @Test
    public void testFindOneBookGeneratesCorrectSQL(){
        underTest.findOne("979-1-3892-8990-0");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookMapper>any(),
                eq("979-1-3892-8990-0")
        );
    }

    @Test
    public void testFindManyBookCanBeGeneratedCorrectly() {
        underTest.find();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookMapper>any()
        );
    }

    @Test
    public void testUpdateBookCanBeGeneratedCorrectly() {
        Book book = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", 1L);
        underTest.create(book);
        book.setTitle("UPDATE");
        underTest.update(book.getIsbn(), book);

        verify(jdbcTemplate).update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(), book.getTitle(), book.getAuthorId(), "979-1-3892-8990-0");

    }

    @Test
    public void testDeleteBooksGeneratesCorrectSQL(){
        Book book = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", 1L);
        underTest.create(book);
        underTest.delete(book.getIsbn());
        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ? ", book.getIsbn());

    }
}
