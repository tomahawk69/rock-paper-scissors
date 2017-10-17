package interview.nokia.test.game;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.List;

public interface Game {

    String getId();
    String getName();
    GameResults calculateResult(String moveFirst, String moveSecond);
    Triple<Long, Long, Long> getStatistics();
    String generateMove();
    boolean isGeneratedMove();
    List<String> getAvailableMoves();
    List<Pair<String, String>> getWinningMoves();
}
