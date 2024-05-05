package com.stuti.database.repositories;

import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanbeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtils.createTestAuthor(2L, "H.G Wells" , 88);
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtils.createTestAuthor(3L, "Lovecraft" , 78);
        underTest.save(authorEntityC);
        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testThatUpdateAuthorsCanBeCreatesAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        AuthorEntity savedEntity = underTest.save(authorEntity);
        savedEntity.setName("UPDATE");
        AuthorEntity updatedEntity = underTest.save(savedEntity);
        Optional<AuthorEntity> result = underTest.findById(updatedEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(updatedEntity);
    }

    @Test
    public void testThatAuthorsCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        AuthorEntity savedEntity = underTest.save(authorEntity);
        underTest.deleteById(savedEntity.getId());
        Optional<AuthorEntity> result = underTest.findById(savedEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorWithAgeLessThan() {
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtils.createTestAuthor(2L, "H.G Wells" , 40);
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtils.createTestAuthor(3L, "Lovecraft" , 24);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(authorEntityB, authorEntityC);

    }

    @Test
    public void testThatGetAuthorWithAgeGreaterThan() {
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthor(1L, "Stephen King" , 76);
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtils.createTestAuthor(2L, "H.G Wells" , 40);
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtils.createTestAuthor(3L, "Lovecraft" , 24);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAuthorWithAgeGreaterThan(50);
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(authorEntityA);

    }

}
