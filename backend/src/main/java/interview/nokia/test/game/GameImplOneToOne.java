package interview.nokia.test.game;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.strategy.GameStrategy;
import interview.nokia.test.types.GameType;

public class GameImplOneToOne extends AbstractGameImpl {
    private static final String NAME = "One-2-One";

    public GameImplOneToOne(GameType gameType, GameStatistic gameStatistic, GameStrategy gameStrategy) {
        super(gameType, gameStatistic, gameStrategy);
        super.isGeneratedMove = false;
        super.name = NAME;
    }

}
