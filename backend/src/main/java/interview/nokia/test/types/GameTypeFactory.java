package interview.nokia.test.types;

import org.springframework.stereotype.Component;

@Component
public class GameTypeFactory {

    public GameType getGameType(final String gameTypeString) {
        final GameTypes gameType = GameTypes.valueOf(gameTypeString);
        switch (gameType) {
            case ROCK_PAPER_SCISSORS: return GameTypeClassic.INSTANCE;
            case LIZARD_SPOCK_SCISSORS_PAPER_ROCK: return GameTypeLizard.INSTANCE;
            default: throw new IllegalArgumentException(String.format("Unsupported game type: %s", gameType));
        }
    }
}
