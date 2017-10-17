package interview.nokia.test.strategy;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.types.GameType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameStrategyRandom implements GameStrategy {
    private static final String STRATEGY_NAME = "Random";
    private static final int RANDOM_LIST_SIZE = 5;

    private final GameStatistic gameStatistic;
    private final GameType gameType;
    private final Random random;

    public GameStrategyRandom(GameType gameType, GameStatistic gameStatistic) {
        this.gameType = gameType;
        this.gameStatistic = gameStatistic;
        this.random = new Random();
    }

    public GameStrategyRandom(GameType gameType, GameStatistic gameStatistic, int seed) {
        this.gameType = gameType;
        this.gameStatistic = gameStatistic;
        this.random = new Random(seed);
    }

    @Override
    public String getName() {
        return STRATEGY_NAME;
    }

    @Override
    public String generateMove() {
        final List<String> stats = gameStatistic.getHistory().stream()
                .map(pp -> pp.getLeft())
                .collect(Collectors.toList());
        List<String> possibleMoves = getPossibleMoves(stats);
        final int randValue = random.nextInt(possibleMoves.size());
        return possibleMoves.get(randValue);
    }

    private List<String> getPossibleMoves(final List<String> stats) {
        if (stats.size() < RANDOM_LIST_SIZE) {
            return gameType.getAvailableMoves();
        } else {
            return stats.subList(Math.max(0, stats.size() - RANDOM_LIST_SIZE), stats.size());
        }
    }
}
