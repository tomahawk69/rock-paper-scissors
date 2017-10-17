package interview.nokia.test.game;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.strategy.GameStrategy;
import interview.nokia.test.types.GameType;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.UUID;

public abstract class AbstractGameImpl implements Game {
    private final String id;
    private final GameType gameType;
    private final GameStatistic gameStatistic;
    private final GameStrategy gameStrategy;
    protected boolean isGeneratedMove;
    protected String name;

    protected AbstractGameImpl(GameType gameType, GameStatistic gameStatistic, GameStrategy gameStrategy) {
        this.gameType = gameType;
        this.gameStatistic = gameStatistic;
        this.gameStrategy = gameStrategy;
        id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isGeneratedMove() {
        return isGeneratedMove;
    }

    @Override
    public List<String> getAvailableMoves() {
        return gameType.getAvailableMoves();
    }

    @Override
    public List<Pair<String, String>> getWinningMoves() {
        return gameType.getWinCombinations();
    }

    @Override
    public GameResults calculateResult(String moveFirst, String moveSecond) {
        final GameResults result = gameType.calculateResult(moveFirst, moveSecond);
        gameStatistic.storeMoves(moveFirst, moveSecond, result);
        return result;
    }

    @Override
    public Triple<Long, Long, Long> getStatistics() {
        return gameStatistic.getStatistics();
    }

    @Override
    public String generateMove() {
        if (!isGeneratedMove) {
            throw new IllegalArgumentException("This game can\'t generate movies");
        } else {
            return gameStrategy.generateMove();
        }
    }

    @Override
    public String getName() {
        // todo implement naming strategy class
        return String.format("%s :: %s :: %s", name, gameType.getName(), gameStrategy.getName());
    }
}
