package interview.nokia.test.types;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class GameTypeClassic extends AbstractGameType {
    public static final GameTypeClassic INSTANCE = new GameTypeClassic();

    private static final String GAME_NAME = "Rock-Paper-Scissors";

    private static final String ROCK = "rock";
    private static final String PAPER = "paper";
    private static final String SCISSORS = "scissors";

    private static final List<String> moves = Arrays.asList(ROCK, PAPER, SCISSORS);

    private static final List<Pair<String, String>> winCombinations = Arrays.asList(
            new ImmutablePair<>(ROCK, SCISSORS),
            new ImmutablePair<>(SCISSORS, PAPER),
            new ImmutablePair<>(PAPER, ROCK)
    );

    @Override
    public String getName() {
        return GAME_NAME;
    }

    @Override
    public List<String> getAvailableMoves() {
        return moves;
    }

    @Override
    public List<Pair<String, String>> getWinCombinations() {
        return winCombinations;
    }
}
