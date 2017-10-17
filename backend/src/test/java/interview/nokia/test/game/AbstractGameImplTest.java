package interview.nokia.test.game;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.statistics.GameStatisticImpl;
import interview.nokia.test.strategy.GameStrategy;
import interview.nokia.test.strategy.GameStrategyRandom;
import interview.nokia.test.types.GameType;
import interview.nokia.test.types.GameTypeClassic;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AbstractGameImplTest {

    private GameType gameType;
    private GameStatistic gameStatistic;
    private GameStrategy gameStrategy;
    private AbstractGameImpl game;

    @Before
    public void setUp() throws Exception {
        gameType = GameTypeClassic.INSTANCE;
        gameStatistic = new GameStatisticImpl();
        gameStrategy = new GameStrategyRandom(gameType, gameStatistic);
        game = new AbstractGameImpl(gameType, gameStatistic, gameStrategy) {
            {
                this.isGeneratedMove = true;
            }
        };
    }

    @Test
    public void shouldCalculateResultAndStoreStatistics() throws Exception {
        assertEquals(new ImmutableTriple<>(0l, 0l, 0l), game.getStatistics());

        String first = gameType.getAvailableMoves().get(0);
        String second = gameType.getAvailableMoves().get(1);
        GameResults gameResult = gameType.calculateResult(first, second);

        game.calculateResult(first, second);

        assertEquals(new ImmutableTriple<>(0l, 1l, 0l), game.getStatistics());
        assertEquals(1, gameStatistic.getHistory().size());
        assertEquals(new ImmutableTriple<>(first, second, gameResult), gameStatistic.getHistory().get(0));

        first = gameType.getAvailableMoves().get(2);
        second = gameType.getAvailableMoves().get(1);
        gameResult = gameType.calculateResult(first, second);

        game.calculateResult(first, second);

        assertEquals(new ImmutableTriple<>(1l, 1l, 0l), game.getStatistics());
        assertEquals(2, gameStatistic.getHistory().size());
        assertEquals(new ImmutableTriple<>(first, second, gameResult), gameStatistic.getHistory().get(1));

    }

    @Test
    public void shouldReturnGeneratedMove() throws Exception {
        final String move = game.generateMove();
        assertNotNull(move);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotReturnGeneratedMove() throws Exception {
        final Game gameWithoutGeneratedMove = new AbstractGameImpl(gameType, gameStatistic, gameStrategy) {
            {
                this.isGeneratedMove = false;
            }
        };
        gameWithoutGeneratedMove.generateMove();
    }
}