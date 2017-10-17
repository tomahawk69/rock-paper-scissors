package interview.nokia.test.types;

import interview.nokia.test.game.GameResults;

public abstract class AbstractGameType implements GameType {

    @Override
    public GameResults calculateResult(final String first, final String second) {
        validateMove(first);
        validateMove(second);
        return getWinCombinations().stream()
                .filter(p -> p.getLeft().equals(first) && p.getRight().equals(second) ||
                        p.getLeft().equals(second) && p.getRight().equals(first))
                .filter(p -> !p.getLeft().equals(p.getRight()))
                .map(p -> p.getLeft().equals(first) ? GameResults.FIRST_WIN : GameResults.SECOND_WIN)
                .findFirst()
                .orElse(GameResults.DRAW);
    }

    public void validateMove(final String move) {
        if (!getAvailableMoves().contains(move)) {
            throw new IllegalArgumentException(String.format("Illegal move: %s. Possible combinations: %s", move, getAvailableMoves()));
        }

    }
}
