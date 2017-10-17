package interview.nokia.test.strategy;

import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.types.GameType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class GameStrategyFactoryTest {

    @Mock
    GameType gameType;

    @Mock
    GameStatistic gameStatistic;

    @Test
    public void shouldCreateStrategy() throws Exception {
        final String strategyName = GameStrategies.RANDOM.name();
        final GameStrategyFactory factory = new GameStrategyFactory();

        final GameStrategy result = factory.createGameStrategy(strategyName, gameType, gameStatistic);

        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateStrategyWithWrongName() throws Exception {
        final String strategyName = "some wrong name";
        final GameStrategyFactory factory = new GameStrategyFactory();

        factory.createGameStrategy(strategyName, gameType, gameStatistic);
    }
}