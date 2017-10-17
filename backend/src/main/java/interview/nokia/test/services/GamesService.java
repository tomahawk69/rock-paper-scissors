package interview.nokia.test.services;

import interview.nokia.test.game.Game;
import interview.nokia.test.statistics.GameStatisticFactory;
import interview.nokia.test.strategy.GameStrategyFactory;
import interview.nokia.test.types.GameTypeFactory;

import java.util.List;

public interface GamesService {

    Game createGame(String gameTypeStr, String gameModeStr, String gameStrategyStr);

    Game gameById(String id);

    List<String> getGameModes();

    List<String> getGameTypes();
}
