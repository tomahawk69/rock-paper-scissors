package interview.nokia.test.statistics;

import interview.nokia.test.game.GameResults;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class GameStatisticImpl implements GameStatistic {
    private final List<Triple<String, String, GameResults>> stats = Collections.synchronizedList(new ArrayList<>());
    private final LongAdder firstWin = new LongAdder();
    private final LongAdder secondWin = new LongAdder();

    @Override
    public void storeMoves(String first, String second, GameResults result) {
        stats.add(new ImmutableTriple<>(first, second, result));
        switch (result) {
            case FIRST_WIN: firstWin.increment();
            break;
            case SECOND_WIN: secondWin.increment();
            break;
            default:
        }
    }

    @Override
    public List<Triple<String, String, GameResults>> getHistory() {
        return stats;
    }

    @Override
    public Triple<Long, Long, Long> getStatistics() {
        long first = firstWin.longValue();
        long second = secondWin.longValue();
        return new ImmutableTriple<>(first, second,stats.size() - first - second);
    }
}

