package com.dom.solver.unit;

import com.dom.solver.dtos.*;
import com.dom.solver.services.DragonApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

@ExtendWith(MockitoExtension.class)
class DragonApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DragonApiService dragonApiService = new DragonApiService();

    @Test
    void startGameShouldReturnGameDtoTest() {
        DragonGameDto mockDragonGameDto = new DragonGameDto();
        mockDragonGameDto.setGameId("test");
        var responseEntity = new ResponseEntity(mockDragonGameDto, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<DragonGameDto>>any())
        ).thenReturn(responseEntity);

        DragonGameDto dragonGameDto = dragonApiService.startGame();

        Mockito.verify(restTemplate).exchange(
                eq("https://dragonsofmugloar.com/api/v2/game/start"),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<DragonGameDto>>any()
        );
        Assertions.assertEquals(mockDragonGameDto, dragonGameDto);
    }

    @Test
    void messagesShouldReturnMessageDtoTest() {
        var gameId = "testId";
        List<MessageDto> mockDtoList = new ArrayList<>();
        mockDtoList.add(new MessageDto());
        mockDtoList.add(new MessageDto());
        ResponseEntity<List<MessageDto>> mockResponseEntity = new ResponseEntity<>(mockDtoList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.GET),
                isNull(),
                Mockito.<ParameterizedTypeReference<List<MessageDto>>>any())
        ).thenReturn(mockResponseEntity);

        List<MessageDto> result = dragonApiService.getMessages(gameId);

        Mockito.verify(restTemplate).exchange(
                eq("https://dragonsofmugloar.com/api/v2/testId/messages"),
                eq(HttpMethod.GET),
                eq(null),
                Mockito.<ParameterizedTypeReference<List<MessageDto>>>any()
        );

        assertNotNull(result);
    }

    @Test
    void reputationInvestigationShouldReturnReputationMessageDtoTest() {
        var gameId = "testId";
        ReputationDto mockReputationDto = new ReputationDto();
        mockReputationDto.setState(1L);
        var responseEntity = new ResponseEntity(mockReputationDto, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<DragonGameDto>>any())
        ).thenReturn(responseEntity);

        ReputationDto reputationInvestigation = dragonApiService.getReputationInvestigation(gameId);

        Mockito.verify(restTemplate).exchange(
                eq("https://dragonsofmugloar.com/api/v2/testId/investigate/reputation"),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<ReputationDto>>any()
        );
        Assertions.assertEquals(mockReputationDto, reputationInvestigation);
    }

    @Test
    void solveMessagesShouldReturnSolveMessageDtoTest() {
        var gameId = "testGameId";
        var messageId = "testMessageId";
        SolvedMessageDto mockSolvedMessageDto = new SolvedMessageDto();
        mockSolvedMessageDto.setMessage("messagetest");
        var responseEntity = new ResponseEntity(mockSolvedMessageDto, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<SolvedMessageDto>>any())
        ).thenReturn(responseEntity);

        var solvedMessageDto = dragonApiService.solveMessage(gameId, messageId);

        Mockito.verify(restTemplate).exchange(
                eq("https://dragonsofmugloar.com/api/v2/testGameId/solve/testMessageId"),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<DragonGameDto>>any()
        );
        Assertions.assertEquals(mockSolvedMessageDto, solvedMessageDto);
    }

    @Test
    void shopShouldReturnShopDtoTest() {
        var gameId = "testId";
        List<ShopDto> mockDtoList = new ArrayList<>();
        mockDtoList.add(new ShopDto());
        mockDtoList.add(new ShopDto());
        ResponseEntity<List<ShopDto>> mockResponseEntity = new ResponseEntity<>(mockDtoList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.GET),
                isNull(),
                Mockito.<ParameterizedTypeReference<List<ShopDto>>>any())
        ).thenReturn(mockResponseEntity);

        List<ShopDto> result = dragonApiService.getShopListings(gameId);

        Mockito.verify(restTemplate).exchange(
                eq("https://dragonsofmugloar.com/api/v2/testId/shop"),
                eq(HttpMethod.GET),
                eq(null),
                Mockito.<ParameterizedTypeReference<List<ShopDto>>>any()
        );

        assertNotNull(result);
    }


    @Test
    void purchaseItemShouldReturnShopDtoTest() {
        var gameId = "testGameId";
        var itemId = "testItemId";
        ShopMessageDto mockDto = new ShopMessageDto();
        var responseEntity = new ResponseEntity(mockDto, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<ShopMessageDto>>any())
        ).thenReturn(responseEntity);

        var shopMessageDto = dragonApiService.purchaseItem(gameId, itemId);

        Mockito.verify(restTemplate).exchange(
                eq("https://dragonsofmugloar.com/api/v2/testGameId/shop/buy/testItemId"),
                eq(HttpMethod.POST),
                Mockito.isNull(),
                Mockito.<ParameterizedTypeReference<ShopMessageDto>>any()
        );
        Assertions.assertEquals(mockDto, shopMessageDto);
    }

}