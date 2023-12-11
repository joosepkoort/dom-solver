package com.dom.solver.services;

import com.dom.solver.configuration.GameOverException;
import com.dom.solver.dtos.MessageDto;
import com.dom.solver.dtos.ShopDto;
import com.dom.solver.dtos.SolvedMessageDto;
import com.dom.solver.model.GameStatus;
import com.dom.solver.model.StepStatistics;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    private static final Integer HEALTH_LEVEL_BEFORE_BUYING_POTION = 5;

    @Resource
    private DragonApiService dragonApiService;

    @Resource
    private DecryptionService decryptionService;

    @Resource
    private ChartService chartService;

    @Resource
    private ProbabilityService probabilityService;

    public void startGame(int gameIndex) throws IOException {
        var game = dragonApiService.startGame();
        var gameId = game.getGameId();
        GameStatus gameStatus = new GameStatus(gameId);
        while (!gameStatus.isGameOver()) {
            try {
                var quests = getAdMessages(gameId);
                solveQuests(gameIndex, quests, gameStatus);
            } catch (GameOverException gameOverException) {
                chartService.saveCharts(gameStatus);
                gameStatus.setGameOver(true);
                break;
            }
        }
        logger.info(String.format("final score of game %s is %s", gameIndex, gameStatus.getCurrentScore()));
    }

    private void solveQuests(int gameIndex, List<MessageDto> quests, GameStatus gameStatus) {
        for (int questIndex = 0; questIndex < quests.size(); questIndex++) {
            var shopListings = dragonApiService.getShopListings(gameStatus.getGameId());
            prepareForQuest(quests, questIndex, gameStatus, shopListings);
            var solvedInfo = tryToSolveQuest(gameStatus, quests.get(questIndex));
            buyItemsWithObtainedGold(solvedInfo, gameStatus, shopListings);
            if (!solvedInfo.adNotFound()) {
                logStatisticsAfterQuest(quests, questIndex, solvedInfo, gameIndex);
                var stepStatistics = new StepStatistics(solvedInfo.getTurn(), solvedInfo.getGold(), solvedInfo.getScore(), solvedInfo.isSuccess(), solvedInfo.getLives(), quests.get(questIndex).getProbabilityValue());
                gameStatus.getStatisticsList().add(stepStatistics);
                if (solvedInfo.getScore() != null) {
                    gameStatus.setCurrentScore(solvedInfo.getScore());
                }
                if (solvedInfo.enoughGoldForHealthPotionAndEnoughLives()) {
                    buyHealthPotion(gameStatus, solvedInfo);
                }
                break;
            }
        }
    }

    private void prepareForQuest(List<MessageDto> quests, int y, GameStatus gameStatus, List<ShopDto> shopListings) {
        if (isDefendingQuestAndCanBuyPotionOfStrongerWings(quests, y, gameStatus)) {
            var potionOfStrongerWings = findItemsToBuy(shopListings, "Potion of Stronger Wings");
            buyFirstItem(gameStatus.getGameId(), potionOfStrongerWings);
        }
    }

    private void buyItemsWithObtainedGold(SolvedMessageDto solvedInfo, GameStatus gameStatus, List<ShopDto> shopListings) {
        if (canBuyArmor(solvedInfo, gameStatus.isArmorExists())) {
            var copperPlating = findItemsToBuy(shopListings, "Copper plating");
            buyFirstItem(gameStatus.getGameId(), copperPlating);
            gameStatus.setArmorExists(true);
        }
        if (canBuyBookOfTricks(solvedInfo, gameStatus.isBookOfTricksExists())) {
            var bookOfTricks = findItemsToBuy(shopListings, "Book of Tricks");
            buyFirstItem(gameStatus.getGameId(), bookOfTricks);
            gameStatus.setBookOfTricksExists(true);
        }
        if (canUpgradeArmor(solvedInfo, gameStatus)) { //upgrade armor
            var ironPlating = findItemsToBuy(shopListings, "Iron plating");
            buyFirstItem(gameStatus.getGameId(), ironPlating);
            gameStatus.setArmorUpgraded(true);
        }
        if (canUpgradeBookOfTricks(solvedInfo, gameStatus)) {
            var bookOfMegatricks = findItemsToBuy(shopListings, "Book of Megatricks");
            buyFirstItem(gameStatus.getGameId(), bookOfMegatricks);
            gameStatus.setBookOfTricksUpgraded(true);
        }
    }

    private static boolean canUpgradeBookOfTricks(SolvedMessageDto solvedInfo, GameStatus gameStatus) {
        return solvedInfo.enoughGoldForBookOfMegaTricks() && gameStatus.isBookOfTricksExists() && !gameStatus.isBookOfTricksUpgraded();
    }

    private static boolean canBuyArmor(SolvedMessageDto solvedInfo, boolean armorExists) {
        return solvedInfo.enoughGoldForCopperPlating() && !armorExists;
    }

    private static boolean canBuyBookOfTricks(SolvedMessageDto solvedInfo, boolean bookOfTricksExists) {
        return solvedInfo.enoughGoldForBookOfTricks() && !bookOfTricksExists;
    }

    private static boolean canUpgradeArmor(SolvedMessageDto solvedInfo, GameStatus gameStatus) {
        return solvedInfo.enoughGoldForIronPlating() && gameStatus.isArmorExists() && !gameStatus.isArmorUpgraded();
    }

    private static boolean isDefendingQuestAndCanBuyPotionOfStrongerWings(List<MessageDto> quests, int questIndex, GameStatus gameStatus) {
        return questMessageContainsKeyword(quests.get(questIndex), "defending") && gameStatus.isArmorExists() && (gameStatus.getGold() >= 100);
    }

    private static void logStatisticsAfterQuest(List<MessageDto> quests, int y, SolvedMessageDto solvedInfo, int i) {
        logger.info(String.format("quest: %s with difficulty %s", quests.get(y).getMessage(), quests.get(y).getProbability()));
        logger.info(String.format("quest info: %s", solvedInfo.getMessage()));
        logger.info(String.format("quest was successful ?: %s", solvedInfo.isSuccess()));
        logger.info(String.format("gold available after quest: %s", solvedInfo.getGold()));
        logger.info(String.format("lives available after quest: %s", solvedInfo.getLives()));
        logger.info(String.format("score for game: %s is %s", i, solvedInfo.getScore()));
    }

    private void buyHealthPotion(GameStatus gameStatus, SolvedMessageDto solvedInfo) {
        var shopListings = dragonApiService.getShopListings(gameStatus.getGameId());
        if (solvedInfo.getLives() != null && solvedInfo.getLives() <= HEALTH_LEVEL_BEFORE_BUYING_POTION) {
            var healingPotions = findItemsToBuy(shopListings, "healing");
            buyFirstItem(gameStatus.getGameId(), healingPotions);
        }
    }

    private static boolean questMessageContainsKeyword(MessageDto messageDto, String keyword) {
        if (messageDto == null) {
            return false;
        }
        return messageDto.getMessage().toUpperCase().contains(keyword.toUpperCase());
    }

    private void buyFirstItem(String gameId, List<ShopDto> itemsToBuy) {
        if (!itemsToBuy.isEmpty()) {
            var firstItem = itemsToBuy.stream().findFirst();
            if (firstItem.isPresent()) {
                dragonApiService.purchaseItem(gameId, firstItem.get().getId());
            }
        }
    }

    private List<ShopDto> findItemsToBuy(List<ShopDto> shopListings, String keyword) {
        return shopListings.stream().filter(shopListing -> {
            var shopItemNameSplit = new ArrayList<>(Arrays.asList(shopListing.getName().split("\\W+")));
            var matches = shopItemNameSplit.stream().filter(y -> probabilityService.wordsSimilarEnough(keyword, y)).toList();
            return !matches.isEmpty();
        }).toList();
    }

    private List<MessageDto> getAdMessages(String gameId) {
        var messages = dragonApiService.getMessages(gameId);
        decryptMessages(messages);
        messages.forEach(probabilityService::setProbabilityScore);
        messages.forEach(messageDto -> {
            if (messageDto.getMessage().toUpperCase().contains("STEAL")) {
                messageDto.setProbabilityValue(-3);
            }
        });
        List<MessageDto> sortedList = new ArrayList<>(messages.stream()
                .sorted(Comparator.comparing(MessageDto::getProbabilityValue)
                        .thenComparing(MessageDto::getReward)).toList());
        Collections.reverse(sortedList);
        return sortedList;
    }

    private void decryptMessages(List<MessageDto> messages) {
        messages.forEach(messageDto -> {
            if (messageDto.isBase64Encrypted()) {
                messageDto.setMessage(decryptionService.decryptBase64(messageDto.getMessage()));
                messageDto.setProbability(decryptionService.decryptBase64(messageDto.getProbability()));
                messageDto.setAdId(decryptionService.decryptBase64(messageDto.getAdId()));
            } else if (messageDto.isRot13Encrypted()) {
                messageDto.setMessage(decryptionService.decryptRot13(messageDto.getMessage()));
                messageDto.setProbability(decryptionService.decryptRot13(messageDto.getProbability()));
                messageDto.setAdId(decryptionService.decryptRot13(messageDto.getAdId()));
            }
        });
    }

    private SolvedMessageDto tryToSolveQuest(GameStatus gameStatus, MessageDto messageDto) {
        var solvedMessageDto = dragonApiService.solveMessage(gameStatus.getGameId(), messageDto.getAdId());
        if (solvedMessageDto.getScore() != null) {
            gameStatus.setCurrentScore(solvedMessageDto.getScore());
        }
        if (solvedMessageDto.getGold() != null) {
            gameStatus.setGold(solvedMessageDto.getScore());
        }
        return solvedMessageDto;
    }

}
