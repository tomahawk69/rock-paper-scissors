package interview.nokia.test.services;

import interview.nokia.test.game.Game;
import interview.nokia.test.game.GameModes;
import interview.nokia.test.strategy.GameStrategies;
import interview.nokia.test.types.GameTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerITest {

    @Autowired
    private GamesService gamesService;

    @Test
    public void shouldCreateGame() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();

        final Game game = gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);

        assertNotNull(game);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateGameIfGameTypeWrong() throws Exception {
        final String gameTypeStr = "some strange id";
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();

        gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateGameIfGameModeWrong() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = GameStrategies.RANDOM.toString();
        final String gameModeStr = "some strange id";

        gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateGameIfGameStrategyWrong() throws Exception {
        final String gameTypeStr = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.toString();
        final String gameStrategyStr = "some strange id";
        final String gameModeStr = GameModes.PERSON_TO_COMPUTER.toString();

        gamesService.createGame(gameTypeStr, gameModeStr, gameStrategyStr);
    }
}
