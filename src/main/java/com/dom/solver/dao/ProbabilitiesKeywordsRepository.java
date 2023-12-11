package com.dom.solver.dao;

import com.dom.solver.model.KeywordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProbabilitiesKeywordsRepository extends JpaRepository<KeywordsEntity, Long> {
    List<KeywordsEntity> findByLevel(Long level);
}
