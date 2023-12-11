package com.dom.solver.interfaces;

import com.dom.solver.dtos.*;

import java.util.List;

public interface DragonApiInterface {
    DragonGameDto startGame();
    ReputationDto getReputationInvestigation(String gameId);
    List<MessageDto> getMessages(String gameId);
    SolvedMessageDto solveMessage(String gameId, String adId);
    List<ShopDto> getShopListings(String gameId);
    ShopMessageDto purchaseItem(String gameId, String itemId);
}
