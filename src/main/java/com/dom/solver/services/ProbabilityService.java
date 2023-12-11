package com.dom.solver.services;

import com.dom.solver.dao.ProbabilitiesKeywordsRepository;
import com.dom.solver.dtos.MessageDto;
import com.dom.solver.model.KeywordsEntity;
import info.debatty.java.stringsimilarity.JaroWinkler;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ProbabilityService {
    private static final Double SIMILARITY_THRESHOLD = 0.95;

    @Resource
    private ProbabilitiesKeywordsRepository keywordsRepository;

    public void setProbabilityScore(MessageDto probability) {
        var veryHighProbabilityKeywordsList = keywordsRepository.findByLevel(2L).stream().map(KeywordsEntity::getValue)
                .map(String::toUpperCase)
                .toList();
        var highProbabilitiesKeywordsList = keywordsRepository.findByLevel(1L).stream().map(KeywordsEntity::getValue)
                .map(String::toUpperCase)
                .toList();
        var lowProbabilityKeywordsList = keywordsRepository.findByLevel(-2L).stream().map(KeywordsEntity::getValue)
                .map(String::toUpperCase)
                .toList();
        var middleProbabilityKeywordsList = keywordsRepository.findByLevel(1L).stream().map(KeywordsEntity::getValue)
                .map(String::toUpperCase)
                .toList();
        var middleLowProbabilityKeywordsList = keywordsRepository.findByLevel(0L).stream().map(KeywordsEntity::getValue)
                .map(String::toUpperCase)
                .toList();
        probability.setProbabilityValue(0);
        var wordsList = new ArrayList<>(Arrays.asList(probability.getProbability().split("\\W+")));
        wordsList.forEach(x -> {
            veryHighProbabilityKeywordsList.forEach(y -> {
                var similarity = new JaroWinkler().similarity(x.toUpperCase(), y.toUpperCase());
                if (similarity >= SIMILARITY_THRESHOLD) {
                    probability.setProbabilityValue(2);
                    return;
                }
            });
            highProbabilitiesKeywordsList.forEach(y -> {
                var similarity = new JaroWinkler().similarity(x.toUpperCase(), y.toUpperCase());
                if (similarity >= SIMILARITY_THRESHOLD) {
                    probability.setProbabilityValue(1);
                    return;
                }
            });
            middleProbabilityKeywordsList.forEach(y -> {
                var similarity = new JaroWinkler().similarity(x.toUpperCase(), y.toUpperCase());
                if (similarity >= SIMILARITY_THRESHOLD) {
                    probability.setProbabilityValue(0);
                    return;
                }
            });
            middleLowProbabilityKeywordsList.forEach(y -> {
                var similarity = new JaroWinkler().similarity(x.toUpperCase(), y.toUpperCase());
                if (similarity >= SIMILARITY_THRESHOLD) {
                    probability.setProbabilityValue(-1);
                    return;
                }
            });
            lowProbabilityKeywordsList.forEach(y -> {
                var similarity = new JaroWinkler().similarity(x.toUpperCase(), y.toUpperCase());
                if (similarity >= SIMILARITY_THRESHOLD) {
                    probability.setProbabilityValue(-2);
                    return;
                }
            });
        });
    }

    public  boolean wordsSimilarEnough(String keyword, String y) {
        var similarity = new JaroWinkler().similarity(y.toUpperCase(), keyword.toUpperCase());
        return (similarity >= SIMILARITY_THRESHOLD);
    }

}
