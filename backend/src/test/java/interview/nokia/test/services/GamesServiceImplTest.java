package interview.nokia.test.services;

import interview.nokia.test.game.Game;
import interview.nokia.test.game.GameModes;
import interview.nokia.test.statistics.GameStatistic;
import interview.nokia.test.statistics.GameStatisticFactory;
import interview.nokia.test.statistics.GameStatisticImpl;
import interview.nokia.test.strategy.GameStrategies;
import interview.nokia.test.strategy.GameStrategy;
import interview.nokia.test.strategy.GameStrategyFactory;
import interview.nokia.test.strategy.GameStrategyRandom;
import interview.nokia.test.types.GameType;
import interview.nokia.test.types.GameTypeFactory;
import interview.nokia.test.types.GameTypeLizard;
import interview.nokia.test.types.GameTypes;
import interview.nokia.test.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GamesServiceImplTest {

    @Mock
    private GameStrategyFactory gameStrategyFactory;
    @Mock
    private GameTypeFactory gameTypeFactory;
    @Mock
    private GameStatisticFactory gameStatisticFactory;

    private GamesService gamesService;

    @Before
    public void setUp() throws Exception {
        gamesService = new GamesServiceImpl(gameStrategyFactory, gameTypeFactory, gameStatisticFactory);
    }

    @Test
    public void shouldCreateGame() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();
        final GameType gameType = GameTypeLizard.INSTANCE;
        final GameStatistic gameStatistic = new GameStatisticImpl();
        final GameStrategy gameStrategy = new GameStrategyRandom(gameType, gameStatistic);

        when(gameTypeFactory.getGameType(gameTypeStr)).thenReturn(gameType);
        when(gameStatisticFactory.createGameStatistic()).thenReturn(gameStatistic);
        when(gameStrategyFactory.createGameStrategy(gameStrategyStr, gameType, gameStatistic)).thenReturn(gameStrategy);

        final Game game = gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);
        assertNotNull(game);

        verify(gameTypeFactory, times(1)).getGameType(gameTypeStr);
        verify(gameStrategyFactory, times(1)).createGameStrategy(gameStrategyStr, gameType, gameStatistic);
        verify(gameStatisticFactory, times(1)).createGameStatistic();
    }

    @Test
    public void shouldReturnGame() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();
        final GameType gameType = GameTypeLizard.INSTANCE;
        final GameStatistic gameStatistic = new GameStatisticImpl();
        final GameStrategy gameStrategy = new GameStrategyRandom(gameType, gameStatistic);

        when(gameTypeFactory.getGameType(gameTypeStr)).thenReturn(gameType);
        when(gameStatisticFactory.createGameStatistic()).thenReturn(gameStatistic);
        when(gameStrategyFactory.createGameStrategy(gameStrategyStr, gameType, gameStatistic)).thenReturn(gameStrategy);

        final Game game = gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);

        final Game storedGame = gamesService.gameById(game.getId());
        assertEquals(game, storedGame);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotReturnGameWithWrongId() throws Exception {
        gamesService.gameById("some unexpected id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfGameTypeWrong() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();

        final GameType gameType = GameTypeLizard.INSTANCE;
        final GameStatistic gameStatistic = new GameStatisticImpl();
        final GameStrategy gameStrategy = new GameStrategyRandom(gameType, gameStatistic);

        when(gameTypeFactory.getGameType(gameTypeStr)).thenThrow(new IllegalArgumentException());
        when(gameStatisticFactory.createGameStatistic()).thenReturn(gameStatistic);
        when(gameStrategyFactory.createGameStrategy(gameStrategyStr, gameType, gameStatistic)).thenReturn(gameStrategy);

        gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfGameStrategyWrong() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();

        final GameType gameType = GameTypeLizard.INSTANCE;
        final GameStatistic gameStatistic = new GameStatisticImpl();
        final GameStrategy gameStrategy = new GameStrategyRandom(gameType, gameStatistic);

        when(gameTypeFactory.getGameType(gameTypeStr)).thenReturn(gameType);
        when(gameStatisticFactory.createGameStatistic()).thenThrow(new IllegalArgumentException());
        when(gameStrategyFactory.createGameStrategy(gameStrategyStr, gameType, gameStatistic)).thenReturn(gameStrategy);

        gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfGameModeWrong() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();

        final GameType gameType = GameTypeLizard.INSTANCE;
        final GameStatistic gameStatistic = new GameStatisticImpl();
        final GameStrategy gameStrategy = new GameStrategyRandom(gameType, gameStatistic);

        when(gameTypeFactory.getGameType(gameTypeStr)).thenReturn(gameType);
        when(gameStatisticFactory.createGameStatistic()).thenReturn(gameStatistic);
        when(gameStrategyFactory.createGameStrategy(gameStrategyStr, gameType, gameStatistic)).thenReturn(gameStrategy);

        gamesService.createGame(gameTypeStr, "some weird mode", gameStrategyStr);
    }

    @Test
    public void shouldReturnGameTypes() throws Exception {
        final List<String> gameTypes = TestUtils.typesToList();

        final List<String> result = gamesService.getGameTypes();

        assertEquals(gameTypes, result);
    }

    @Test
    public void shouldReturnGameModes() throws Exception {
        final List<String> gameModes = TestUtils.modesToList();

        final List<String> result = gamesService.getGameModes();

        assertEquals(gameModes, result);
    }

}