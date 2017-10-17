package interview.nokia.test.types;

import interview.nokia.test.game.GameResults;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Rules of the game
 */
public interface GameType {

    String getName();
    List<String> getAvailableMoves();
    List<Pair<String, String>> getWinCombinations();
    GameResults calculateResult(final String first, final String second);
}
