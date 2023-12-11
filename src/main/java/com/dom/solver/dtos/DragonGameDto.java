package com.dom.solver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DragonGameDto {
    String gameId;
    Long lives;
    Long gold;
    Long level;
    Long score;
    Long highScore;
    Long turn;
}
