package com.dom.solver.integration;

import com.dom.solver.dao.ProbabilitiesKeywordsRepository;
import com.dom.solver.model.KeywordsEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-prod.yml")
@AutoConfigureWebClient(registerRestTemplate = true)
class ProbabilityKeywordsRepositoryTest {

    @Autowired
    ProbabilitiesKeywordsRepository keywordsRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewKeyword_whenSave_thenSuccess() {
        KeywordsEntity KeywordsEntity = new KeywordsEntity(1L, "testcriteria", 2L, "testadditionalcriteria");

        KeywordsEntity insertedKeywordsEntity = keywordsRepository.save(KeywordsEntity);

        assertThat(entityManager.find(KeywordsEntity.class, insertedKeywordsEntity.getId())).isEqualTo(KeywordsEntity);
    }

    @Test
    void givenKeywordCreated_whenUpdate_thenSuccess() {
        KeywordsEntity keywordsEntity = new KeywordsEntity(2L, "testcriteria", 1L, "testadditionalcriteria");
        String newValue = "high";
        keywordsEntity.setValue(newValue);

        keywordsRepository.save(keywordsEntity);

        assertThat(entityManager.find(KeywordsEntity.class, keywordsEntity.getId()).getValue()).isEqualTo(newValue);
    }

    @Test
    void givenKeywordCreated_whenFindById_thenSuccess() {
        KeywordsEntity keywordsEntity = new KeywordsEntity(1L, "testcriteria", 0L, "testadditionalcriteria");
        keywordsRepository.save(keywordsEntity);

        Optional<KeywordsEntity> retrievedKeywordsEntity = keywordsRepository.findById(keywordsEntity.getId());

        assertThat(retrievedKeywordsEntity).contains(keywordsEntity);
    }

}