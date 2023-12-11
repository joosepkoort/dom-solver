package com.dom.solver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StepStatistics {
    long step = 0;
    long gold = 0;
    long score = 0;
    boolean successful;
    long currentLives = 0;
    int difficulty;
}
