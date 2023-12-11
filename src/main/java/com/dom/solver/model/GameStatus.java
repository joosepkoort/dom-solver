package com.dom.solver.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GameStatus {
    boolean armorExists = false;
    boolean armorUpgraded = false;
    boolean bookOfTricksExists = false;
    boolean bookOfTricksUpgraded = false;
    boolean gameOver = false;
    Long currentScore = 0L;
    Long gold = 0L;
    String gameId;
    List<StepStatistics> statisticsList = new ArrayList<>();

    public GameStatus(String gameId) {
        this.gameId = gameId;
    }
}
