package com.dom.solver.services;

import com.dom.solver.dtos.*;
import com.dom.solver.interfaces.DragonApiInterface;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DragonApiService implements DragonApiInterface {

    private static final String API_URL = "https://dragonsofmugloar.com/api/v2/";
    private static final String START_GAME_URL = "game/start";
    private static final String INVESTIGATION_URL = "/investigate/reputation";
    private static final String MESSAGES_URL = "/messages";
    private static final String SOLVE_MESSAGES_URL = "/solve";
    private static final String SHOP_URL = "/shop";

    @Resource
    private RestTemplate restTemplate;

    public DragonGameDto startGame() {
        ResponseEntity<DragonGameDto> responseEntity = restTemplate.exchange(
                API_URL + START_GAME_URL,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public ReputationDto getReputationInvestigation(String gameId) {
        ResponseEntity<ReputationDto> responseEntity = restTemplate.exchange(
                API_URL + gameId + INVESTIGATION_URL,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public List<MessageDto> getMessages(String gameId) {
        ResponseEntity<List<MessageDto>> responseEntity = restTemplate.exchange(
                API_URL + gameId + MESSAGES_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public SolvedMessageDto solveMessage(String gameId, String adId) {
        ResponseEntity<SolvedMessageDto> responseEntity = restTemplate.exchange(
                API_URL + gameId + SOLVE_MESSAGES_URL + '/' + adId,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public List<ShopDto> getShopListings(String gameId) {
        ResponseEntity<List<ShopDto>> responseEntity = restTemplate.exchange(
                API_URL + gameId + SHOP_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }

    public ShopMessageDto purchaseItem(String gameId, String itemId) {
        ResponseEntity<ShopMessageDto> responseEntity = restTemplate.exchange(
                API_URL + gameId + SHOP_URL + "/buy/" + itemId,
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseEntity.getBody();
    }
}
