package interview.nokia.test.utils;

import interview.nokia.test.game.GameModes;
import interview.nokia.test.types.GameTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static List<String> modesToList() {
        return Arrays.stream(GameModes.values()).map(Enum::toString).collect(Collectors.toList());
    }

    public static List<String> typesToList() {
        return Arrays.stream(GameTypes.values()).map(Enum::toString).collect(Collectors.toList());
    }
}
