package com.stuti.database.repositories;

import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.stuti.database.domain.entities.BookEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanbeCreatedandRecalled(){
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        BookEntity bookEntityA = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", authorEntityA);
        underTest.save(bookEntityA);
        AuthorEntity authorEntityB = TestDataUtils.createTestAuthor(2L, "H.G Wells" , 88);
        BookEntity bookEntityB = TestDataUtils.createTestBook("979-2-3892-8990-1", "The Invisible Man", authorEntityB);
        underTest.save(bookEntityB);
        AuthorEntity authorEntityC = TestDataUtils.createTestAuthor(3L, "Lovecraft" , 78);
        BookEntity bookEntityC = TestDataUtils.createTestBook("979-3-3892-8990-2", "Dracula", authorEntityC);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBooksCanBeUpdateAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", authorEntity);
        underTest.save(bookEntity);
        bookEntity.setTitle("UPDATE");
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatBooksCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", authorEntity);
        underTest.save(bookEntity);
        underTest.deleteById(bookEntity.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();
    }
}
