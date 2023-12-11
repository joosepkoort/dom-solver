package com.dom.solver.unit;

import com.dom.solver.configuration.GameOverException;
import com.dom.solver.dtos.DragonGameDto;
import com.dom.solver.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {
    @InjectMocks
    GameService gameService;

    @Mock
    DragonApiService dragonApiService;

    @Mock
    ProbabilityService probabilityService;

    @Mock
    ChartService chartService;

    @Test
    void shouldStartGameTest() throws IOException {
        int index = 0;
        var dragonGameDto = new DragonGameDto();
        dragonGameDto.setGameId("testGameId");
        var questId = "testQuestId";
        initMocks(dragonGameDto, questId);

        gameService.startGame(index);

        verify(dragonApiService, times(1)).startGame();
    }

    private void initMocks(DragonGameDto dragonGameDto, String questId) {
        when(dragonApiService.startGame()).thenReturn(dragonGameDto);
        doNothing().when(probabilityService).setProbabilityScore(any());
        when(dragonApiService.getShopListings(dragonGameDto.getGameId())).thenReturn(null);
        when(dragonApiService.solveMessage(dragonGameDto.getGameId(), questId)).thenReturn(null);
        doThrow(GameOverException.class).when(dragonApiService).getMessages(any());
    }
}