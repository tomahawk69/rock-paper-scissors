package interview.nokia.test.game;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.strategy.GameStrategy;
import interview.nokia.test.types.GameType;

public class GameImpOneToComp extends AbstractGameImpl {
    private static final String NAME = "One-2-Computer";

    public GameImpOneToComp(GameType gameType, GameStatistic gameStatistic, GameStrategy gameStrategy) {
        super(gameType, gameStatistic, gameStrategy);
        super.isGeneratedMove = true;
        super.name = NAME;
    }

}
