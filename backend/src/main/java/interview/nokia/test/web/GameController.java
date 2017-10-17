package interview.nokia.test.web;

import interview.nokia.test.game.Game;
import interview.nokia.test.services.GamesService;
import interview.nokia.test.strategy.GameStrategies;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class GameController {
    private final static Logger LOGGER = LogManager.getLogger(GameController.class);
    public static final String ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument";
    private final GamesService gamesService;

    @Autowired
    public GameController(final GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @RequestMapping(value = "/modes", method = RequestMethod.GET)
    public List<String> getGameModes() {
        return gamesService.getGameModes();
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public List<String> getGameTypes() {
        return gamesService.getGameTypes();
    }

    @RequestMapping(value = "/new/{gameType}/{gameMode}", method = RequestMethod.POST)
    public String createNewGame(@PathVariable String gameType, @PathVariable String gameMode) {
        Game game = gamesService.createGame(gameType, gameMode, GameStrategies.RANDOM.toString());
        return game.getId();
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String getGameName(@PathVariable String id) {
        LOGGER.debug(String.format("getGameName %s", id));
        final Game game = gamesService.gameById(id);
        return game.getName();
    }

    @RequestMapping(value = "/stat/{id}", method = RequestMethod.GET)
    public Long[] getGameStat(@PathVariable String id) {
        LOGGER.debug(String.format("getGameStat %s", id));
        final Game game = gamesService.gameById(id);
        Triple<Long, Long, Long> stat = game.getStatistics();
        return new Long[]{stat.getLeft(), stat.getMiddle(), stat.getRight()};
    }

    @RequestMapping(value = "/moves/{id}", method = RequestMethod.GET)
    public String[] getGameMoves(@PathVariable String id) {
        LOGGER.debug(String.format("getGamesMoves %s", id));
        final Game game = gamesService.gameById(id);
        final Collection<String> result = game.getAvailableMoves();
        return result.toArray(new String[result.size()]);
    }

    @RequestMapping(value = "/winning/{id}", method = RequestMethod.GET)
    public List<Pair<String, String>> getWinningMoves(@PathVariable String id) {
        LOGGER.debug(String.format("getGamesMoves %s", id));
        final Game game = gamesService.gameById(id);
        return game.getWinningMoves();
    }

    @RequestMapping(value = "/isgeneratedmove/{id}", method = RequestMethod.GET)
    public Boolean isGeneratedMove(@PathVariable String id) {
        LOGGER.debug(String.format("isGenerateMove %s ", id));
        final Game game = gamesService.gameById(id);
        return game.isGeneratedMove();
    }

    @RequestMapping(value = "/generate/{id}", method = RequestMethod.GET)
    public String generateMove(@PathVariable String id) {
        LOGGER.debug(String.format("generateMove %s", id));
        final Game game = gamesService.gameById(id);
        return game.generateMove();
    }

    @RequestMapping(value = "/calculate/{id}/{first}/{second}", method = RequestMethod.GET)
    public String calculateResult(@PathVariable String id, @PathVariable String first, @PathVariable String second) {
        LOGGER.debug(String.format("calculateResult %s, %s, %s", id, first, second));
        final Game game = gamesService.gameById(id);
        return game.calculateResult(first, second).toString();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException e)
    {
        LOGGER.error(e);
        ResponseError result = new ResponseError(ILLEGAL_ARGUMENT_MESSAGE, e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
