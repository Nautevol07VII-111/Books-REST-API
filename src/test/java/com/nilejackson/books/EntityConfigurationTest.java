package com.nilejackson.books;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nilejackson.books.domain.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;


import jakarta.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EntityConfigurationTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    public void contextLoads() {
        assertNotNull(entityManager);
    }

    @Test
    public void cantFindBookEntityMetadata() {
        assertNotNull(entityManager.getMetamodel().entity(BookEntity.class));
    }

    
   
}
