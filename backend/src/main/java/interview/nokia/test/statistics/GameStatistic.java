package interview.nokia.test.statistics;

import interview.nokia.test.game.GameResults;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public interface GameStatistic {
    void storeMoves(String first, String second, GameResults result);
    List<Triple<String, String, GameResults>> getHistory();
    Triple<Long, Long, Long> getStatistics();
}
