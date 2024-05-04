package com.stuti.database.dao.impl;

import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.stuti.database.domain.Book;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;

    private AuthorDaoImpl authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDaoImpl authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanbeCreatedandRecalled(){
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        authorDao.create(author);
        Book book = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", 1L);
        underTest.create(book);
        book.setAuthorId(author.getId());
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        authorDao.create(authorA);
        Book bookA = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", authorA.getId());
        underTest.create(bookA);
        Author authorB = TestDataUtils.createTestAuthor(2L, "H.G Wells" , 88);
        authorDao.create(authorB);
        Book bookB = TestDataUtils.createTestBook("979-2-3892-8990-1", "The Invisible Man", authorB.getId());
        underTest.create(bookB);
        Author authorC = TestDataUtils.createTestAuthor(3L, "Lovecraft" , 78);
        authorDao.create(authorC);
        Book bookC = TestDataUtils.createTestBook("979-3-3892-8990-2", "Dracula", authorC.getId());
        underTest.create(bookC);

        List<Book> result = underTest.find();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBooksCanBeUpdateAndRecalled() {
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        authorDao.create(author);
        Book book = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", 1L);
        underTest.create(book);
        book.setTitle("UPDATE");
        underTest.update(book.getIsbn(), book);

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBooksCanBeDeleted(){
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        authorDao.create(author);
        Book book = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", 1L);
        underTest.create(book);
        underTest.delete(book.getIsbn());
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isEmpty();
    }
}
