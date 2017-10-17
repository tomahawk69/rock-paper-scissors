package interview.nokia.test.types;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class GameTypeLizard extends AbstractGameType {
    public static final GameType INSTANCE = new GameTypeLizard();
    private static final String GAME_NAME = "Lizard-Spock-Scissors-Paper-Rock";

    private static final String LIZARD = "lizard";
    private static final String SPOCK = "spock";
    private static final String SCISSORS = "scissors";
    private static final String PAPER = "paper";
    private static final String ROCK = "rock";

    private static final List<String> moves = Arrays.asList(LIZARD, SPOCK, SCISSORS, PAPER, ROCK);
    private static final List<Pair<String, String>> winCombinations = Arrays.asList(
            new ImmutablePair<>(SPOCK, SCISSORS),
            new ImmutablePair<>(SPOCK, ROCK),
            new ImmutablePair<>(ROCK, SCISSORS),
            new ImmutablePair<>(ROCK, LIZARD),
            new ImmutablePair<>(SCISSORS, PAPER),
            new ImmutablePair<>(SCISSORS, LIZARD),
            new ImmutablePair<>(PAPER, ROCK),
            new ImmutablePair<>(PAPER, SPOCK),
            new ImmutablePair<>(LIZARD, SPOCK),
            new ImmutablePair<>(LIZARD, PAPER)
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
