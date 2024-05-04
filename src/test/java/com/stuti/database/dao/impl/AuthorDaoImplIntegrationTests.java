package com.stuti.database.dao.impl;

import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.Author;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanbeCreatedAndRecalled() {
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(author);
        Optional<Author> result = underTest.findOne(1L);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(authorA);
        Author authorB = TestDataUtils.createTestAuthor(2L, "H.G Wells" , 88);
        underTest.create(authorB);
        Author authorC = TestDataUtils.createTestAuthor(3L, "Lovecraft" , 78);
        underTest.create(authorC);
        List<Author> result = underTest.find();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatUpdateAuthorsCanBeCreatesAndRecalled() {
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(author);
        author.setName("UPDATE");
        underTest.update(1L, author);
        Optional<Author> result = underTest.findOne(1L);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorsCanBeDeleted(){
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(author);
        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isEmpty();
    }

}
