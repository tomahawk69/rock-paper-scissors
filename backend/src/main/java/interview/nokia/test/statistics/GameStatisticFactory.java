package interview.nokia.test.statistics;

import org.springframework.stereotype.Component;

@Component
public class GameStatisticFactory {

    public GameStatistic createGameStatistic() {
        return new GameStatisticImpl();
    }

}
