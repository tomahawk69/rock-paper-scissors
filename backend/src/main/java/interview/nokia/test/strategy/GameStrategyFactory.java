package interview.nokia.test.strategy;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.types.GameType;
import org.springframework.stereotype.Component;

@Component
public class GameStrategyFactory {

    public GameStrategy createGameStrategy(String strategyName, GameType gameType, GameStatistic gameStatistic) {
        GameStrategies gameStrategy = GameStrategies.valueOf(strategyName);
        if (gameStrategy == GameStrategies.RANDOM) {
            return new GameStrategyRandom(gameType, gameStatistic);
        } else {
            throw new IllegalArgumentException(String.format("Unsupported game strategy: %s", gameStrategy));
        }
    }
}
