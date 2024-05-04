package com.stuti.database.dao.impl;

import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratedCorrectSQL(){
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("Stephen King"), eq(76)
        );
    }

    @Test
    public void testThatFindOneAuthorGeneratedCorrectSQL(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1 "),
                ArgumentMatchers.<AuthorDaoImpl.AuthorMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindManyAuthorGenerateCorrectSQL(){

        List<Author> result = underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorMapper>any()
        );
    }

    @Test
    public void testThatUpdateAuthorGeneratesCorrectly() {
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(author);
        author.setName("UPDATE");
        underTest.update(1L, author);
        verify(jdbcTemplate).update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ? ",
                author.getId(), author.getName(), author.getAge(), 1L);
    }

    @Test
    public void testThatDeleteAuthorGeneratesCorrectSQL(){
        Author author = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.create(author);
        underTest.delete(author.getId());
        verify(jdbcTemplate).update("DELETE FROM authors where id = ? ", author.getId());
    }
}
