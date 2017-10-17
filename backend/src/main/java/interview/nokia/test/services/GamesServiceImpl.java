package interview.nokia.test.services;

import interview.nokia.test.game.Game;
import interview.nokia.test.game.GameImpOneToComp;
import interview.nokia.test.game.GameImplOneToOne;
import interview.nokia.test.game.GameModes;
import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.statistics.GameStatisticFactory;
import interview.nokia.test.strategy.GameStrategy;
import interview.nokia.test.strategy.GameStrategyFactory;
import interview.nokia.test.types.GameType;
import interview.nokia.test.types.GameTypeFactory;
import interview.nokia.test.types.GameTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GamesServiceImpl implements GamesService {
    private final Map<String, Game> games = new ConcurrentHashMap<>();
    private final GameStrategyFactory gameStrategyFactory;
    private final GameTypeFactory gameTypeFactory;
    private final GameStatisticFactory gameStatisticFactory;

    @Autowired
    public GamesServiceImpl(GameStrategyFactory gameStrategyFactory,
                            GameTypeFactory gameTypeFactory,
                            GameStatisticFactory gameStatisticFactory) {
        this.gameStrategyFactory = gameStrategyFactory;
        this.gameTypeFactory = gameTypeFactory;
        this.gameStatisticFactory = gameStatisticFactory;
    }

    @Override
    public Game createGame(String gameTypeStr, String gameModeStr, String gameStrategyStr) {
        final GameType gameType = gameTypeFactory.getGameType(gameTypeStr);
        final GameStatistic gameStatistic = gameStatisticFactory.createGameStatistic();
        final GameStrategy gameStrategy = gameStrategyFactory.createGameStrategy(gameStrategyStr, gameType, gameStatistic);

        final Game result = gameModeStrToGameMode(gameModeStr, gameType, gameStrategy, gameStatistic);

        games.put(result.getId(), result);

        return result;
    }

    @Override
    public Game gameById(final String id) {
        final Game result = games.get(id);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Can't find game with such id: %s", id));
        }
        return result;
    }

    // todo replace with a factory
    private Game gameModeStrToGameMode(final String gameModeStr,
                                       final GameType gameType,
                                       final GameStrategy gameStrategy,
                                       final GameStatistic gameStatistic) {
        final GameModes gameMode = GameModes.valueOf(gameModeStr);
        switch (gameMode) {
            case PERSON_TO_COMPUTER:
                return new GameImpOneToComp(gameType, gameStatistic, gameStrategy);
            case PERSON_TO_PERSON:
                return new GameImplOneToOne(gameType, gameStatistic, gameStrategy);
            default:
                throw new IllegalArgumentException(String.format("Not supported game mode: %s", gameMode));
        }
    }

    @Override
    public List<String> getGameModes() {
        return Arrays.stream(GameModes.values()).map(Enum::toString).collect(Collectors.toList());
    }

    @Override
    public List<String> getGameTypes() {
        return Arrays.stream(GameTypes.values()).map(Enum::toString).collect(Collectors.toList());
    }
}
