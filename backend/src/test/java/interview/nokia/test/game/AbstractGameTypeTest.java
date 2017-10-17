package interview.nokia.test.game;

import interview.nokia.test.types.AbstractGameType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AbstractGameTypeTest {
    private static final String ONE = "one";
    private static final String TWO = "two";
    private static final String THREE = "three";

    private static final List<String> availableMoves = Arrays.asList(ONE, TWO, THREE);
    private static final List<Pair<String, String>> winners = Arrays.asList(
            new ImmutablePair<>(ONE, THREE),
            new ImmutablePair<>(THREE, TWO)
    );

    private AbstractGameType gameType;

    @Before
    public void setUp() throws Exception {
        gameType = new AbstractGameType() {

            @Override
            public String getName() {
                return "test";
            }

            @Override
            public List<String> getAvailableMoves() {
                return availableMoves;
            }

            @Override
            public List<Pair<String, String>> getWinCombinations() {
                return winners;
            }
        };
    }

    @Test
    public void shouldReturnFirstWinner() throws Exception {
        final GameResults expectedResult = GameResults.FIRST_WIN;

        final GameResults result = gameType.calculateResult(ONE, THREE);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldReturnDrawIfOneMoveAbsent() throws Exception {
        final GameResults expectedResult = GameResults.DRAW;

        final GameResults result = gameType.calculateResult(ONE, TWO);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldReturnDrawIfBothMovesAreTheSame() throws Exception {
        final GameResults expectedResult = GameResults.DRAW;

        final GameResults result = gameType.calculateResult(ONE, ONE);

        assertEquals(expectedResult, result);
    }

    @Test
    public void shouldReturnSecondWinner() throws Exception {
        final GameResults expectedResult = GameResults.SECOND_WIN;

        final GameResults result = gameType.calculateResult(TWO, THREE);

        assertEquals(expectedResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptValidMoves() throws Exception {
        gameType.validateMove("some wrong move");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptNullMoves() throws Exception {
        gameType.validateMove(null);
    }

}