package com.dom.solver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolvedMessageDto {
    boolean success;
    Long lives;
    Integer gold;
    Long score;
    Long highScore;
    Long turn;
    String message;
    String error;

    private boolean enoughGoldForClawSharpening() {
        return gold != null && gold >= 100;
    }

    public boolean enoughGoldForPotionOfStrongerWings() {
        return gold != null && gold >= 100;
    }

    public boolean enoughGoldForClawHoning() {
        return gold != null && gold >= 300;
    }

    public boolean enoughGoldForPotionOfAwesomeWings() {
        return gold != null && gold >= 300;
    }

    public boolean enoughGoldForCopperPlating() {
        return gold != null && gold >= 100;
    }

    public boolean enoughGoldForIronPlating() {
        return gold != null && gold >= 300;
    }

    public boolean enoughGoldForBookOfTricks() {
        return gold != null && gold >= 100;
    }

    public boolean enoughGoldForBookOfMegaTricks() {
        return gold != null && gold >= 300;
    }

    public boolean enoughGoldForHealthPotionAndEnoughLives() {
        return gold != null && gold >= 50 && lives != null && lives <= 3;
    }

    public boolean adNotFound() {
        return error != null && error.contains("No ad by this ID exists");
    }
}
