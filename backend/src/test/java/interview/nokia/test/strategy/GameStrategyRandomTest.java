package interview.nokia.test.strategy;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.statistics.GameStatisticImpl;
import interview.nokia.test.game.GameResults;
import interview.nokia.test.types.GameType;
import interview.nokia.test.types.GameTypeClassic;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class GameStrategyRandomTest {
    private GameType gameType;
    private GameStatistic gameStatistic;

    @Before
    public void setUp() throws Exception {
        gameType = new GameTypeClassic();
        gameStatistic = new GameStatisticImpl();
    }

    @Test
    public void shouldReturnRandomValues() throws Exception {
        final int seed = 111;
        final GameStrategy gameStrategy = new GameStrategyRandom(gameType, gameStatistic, seed);

        // r=1
        String move1 = gameStrategy.generateMove();
        String expectedResult = gameType.getAvailableMoves().get(1);
        assertEquals(expectedResult, move1);
        gameStatistic.storeMoves("1", expectedResult, GameResults.DRAW);

        // r=2
        String move2 = gameStrategy.generateMove();
        expectedResult = gameType.getAvailableMoves().get(2);
        assertEquals(expectedResult, move2);
        gameStatistic.storeMoves("2", expectedResult, GameResults.DRAW);

        // r=1
        String move3 = gameStrategy.generateMove();
        expectedResult = gameType.getAvailableMoves().get(1);
        assertEquals(expectedResult, move3);
        gameStatistic.storeMoves("3", expectedResult, GameResults.DRAW);

        // r=2
        String move4 = gameStrategy.generateMove();
        expectedResult = gameType.getAvailableMoves().get(2);
        assertEquals(expectedResult, move4);
        gameStatistic.storeMoves("4", expectedResult, GameResults.DRAW);

        // r=2
        String move5 = gameStrategy.generateMove();
        expectedResult = gameType.getAvailableMoves().get(2);
        assertEquals(expectedResult, move5);
        gameStatistic.storeMoves("5", expectedResult, GameResults.DRAW);

        // r=0
        String move6 = gameStrategy.generateMove();
        expectedResult = gameStatistic.getHistory().get(0).getLeft();
        assertEquals(expectedResult, move6);
        gameStatistic.storeMoves("6", expectedResult, GameResults.DRAW);

        // r=4
        String move7 = gameStrategy.generateMove();
        expectedResult = gameStatistic.getHistory().get(5).getLeft();
        assertEquals(expectedResult, move7);

    }
}