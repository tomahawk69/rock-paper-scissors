package interview.nokia.test.web;

import interview.nokia.test.game.GameModes;
import interview.nokia.test.game.GameResults;
import interview.nokia.test.types.GameType;
import interview.nokia.test.types.GameTypeLizard;
import interview.nokia.test.types.GameTypes;
import interview.nokia.test.utils.TestUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static interview.nokia.test.web.GameController.ILLEGAL_ARGUMENT_MESSAGE;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GameController gameController;

    @Test
    public void shouldReturnModes() throws Exception {
        final List<String> modes = TestUtils.modesToList();

        mvc.perform(MockMvcRequestBuilders.get("/modes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(modes.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(modes.get(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(modes.get(1))));
    }

    @Test
    public void shouldReturnTypes() throws Exception {
        final List<String> types = TestUtils.typesToList();

        mvc.perform(MockMvcRequestBuilders.get("/types")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(types.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(types.get(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(types.get(1))));
    }

    @Test
    public void successGameScenarioLizardPersonToComputer() throws Exception {
        final GameType game = GameTypeLizard.INSTANCE;
        final String gameType = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.name();
        final String gameMode = GameModes.PERSON_TO_COMPUTER.name();
        final String newGameType = GameTypes.ROCK_PAPER_SCISSORS.name();
        final String newGameMode = GameModes.PERSON_TO_PERSON.name();

        final MvcResult gameIdResponse = mvc.perform(MockMvcRequestBuilders.post(String.format("/new/%s/%s", gameType, gameMode), "")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String gameId = gameIdResponse.getResponse().getContentAsString();
        assertThat(gameId, is(not(isEmptyString())));

        final MvcResult gameInfoResponse = mvc.perform(MockMvcRequestBuilders.get(String.format("/info/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String gameInfo = gameInfoResponse.getResponse().getContentAsString();
        assertThat(gameInfo, is(not(isEmptyString())));

        mvc.perform(MockMvcRequestBuilders.get(String.format("/moves/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(game.getAvailableMoves().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(game.getAvailableMoves().get(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(game.getAvailableMoves().get(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", is(game.getAvailableMoves().get(2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3]", is(game.getAvailableMoves().get(3))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4]", is(game.getAvailableMoves().get(4))));

        mvc.perform(MockMvcRequestBuilders.get(String.format("/winning/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(game.getWinCombinations().size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.hasKey(game.getWinCombinations().get(0).getKey())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.hasValue(game.getWinCombinations().get(0).getValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Matchers.hasKey(game.getWinCombinations().get(1).getKey())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Matchers.hasValue(game.getWinCombinations().get(1).getValue())));

        final MvcResult gameIsGenerateMoveResult = mvc.perform(MockMvcRequestBuilders.get(String.format("/isgeneratedmove/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String gameIsGenerateMove = gameIsGenerateMoveResult.getResponse().getContentAsString();
        assertEquals("true", gameIsGenerateMove);

        // initial state

        mvc.perform(MockMvcRequestBuilders.get(String.format("/stat/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", is(0)))
                .andReturn();

        // game

        // we win
        generateResponse(game, gameId, game.getAvailableMoves(), true);

        mvc.perform(MockMvcRequestBuilders.get(String.format("/stat/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", is(0)))
                .andReturn();

        // we lost
        generateResponse(game, gameId, game.getAvailableMoves(), false);

        // we win
        generateResponse(game, gameId, game.getAvailableMoves(), true);

        // we lost
        generateResponse(game, gameId, game.getAvailableMoves(), false);

        // we win
        generateResponse(game, gameId, game.getAvailableMoves(), true);

        // we win
        generateResponse(game, gameId, game.getAvailableMoves(), true);

        // we lost
        generateResponse(game, gameId, game.getAvailableMoves(), false);

        // 5th turn: draw
        generateDrawResponse(gameId);

        // 5th turn: draw again
        generateDrawResponse(gameId);


        // final state
        mvc.perform(MockMvcRequestBuilders.get(String.format("/stat/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", is(2)))
                .andReturn();

        // start new game

        final MvcResult newGameIdResponse = mvc.perform(MockMvcRequestBuilders.post(String.format("/new/%s/%s", newGameType, newGameMode), "")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String newGameId = newGameIdResponse.getResponse().getContentAsString();
        assertNotEquals(gameId, newGameId);

        mvc.perform(MockMvcRequestBuilders.get(String.format("/stat/%s", newGameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", is(0)))
                .andReturn();
    }

    @Test
    public void failGameScenarioWrongId() throws Exception {

        validateWrongIdResponse("/info");
        validateWrongIdResponse("/moves");
        validateWrongIdResponse("/generate");
        validateWrongIdResponse("/isgeneratedmove");
        mvc.perform(MockMvcRequestBuilders.get("/calculate/some id/first/second")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", is(ILLEGAL_ARGUMENT_MESSAGE)));

    }

    private void validateWrongIdResponse(String url) throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(String.format("%s/%s", url, "some weird id"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", is(ILLEGAL_ARGUMENT_MESSAGE)));
    }

    @Test
    public void failGameScenarioWrongMove() throws Exception {

        final GameType game = GameTypeLizard.INSTANCE;
        final String gameType = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.name();
        final String gameMode = GameModes.PERSON_TO_COMPUTER.name();

        final MvcResult gameIdResponse = mvc.perform(MockMvcRequestBuilders.post(String.format("/new/%s/%s", gameType, gameMode), "")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String gameId = gameIdResponse.getResponse().getContentAsString();

        mvc.perform(MockMvcRequestBuilders.get(String.format("/calculate/%s/%s/%s", gameId, game.getAvailableMoves().get(0), "some weird move"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", is(ILLEGAL_ARGUMENT_MESSAGE)));

        mvc.perform(MockMvcRequestBuilders.get(String.format("/calculate/%s/%s/%s", gameId, "some weird move", game.getAvailableMoves().get(0)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", is(ILLEGAL_ARGUMENT_MESSAGE)));
    }

    @Test
    public void failGameScenarioDoNotGenerate() throws Exception {

        final String gameType = GameTypes.LIZARD_SPOCK_SCISSORS_PAPER_ROCK.name();
        final String gameMode = GameModes.PERSON_TO_PERSON.name();

        final MvcResult gameIdResponse = mvc.perform(MockMvcRequestBuilders.post(String.format("/new/%s/%s", gameType, gameMode), "")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String gameId = gameIdResponse.getResponse().getContentAsString();

        mvc.perform(MockMvcRequestBuilders.get(String.format("/generate/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", is(ILLEGAL_ARGUMENT_MESSAGE)));
    }

    private void generateResponse(final GameType gameType, final String gameId, List<String> availableMoves, final boolean toWin) throws Exception {
        final MvcResult moveResult = mvc.perform(MockMvcRequestBuilders.get(String.format("/generate/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String move = moveResult.getResponse().getContentAsString();
        final String ourMove = generateResponseMove(gameType, move, availableMoves, toWin);

        calculateResult(gameId, move, ourMove, toWin ? GameResults.FIRST_WIN.name() : GameResults.SECOND_WIN.name());
    }

    private void generateDrawResponse(final String gameId) throws Exception {
        final MvcResult moveResult = mvc.perform(MockMvcRequestBuilders.get(String.format("/generate/%s", gameId))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        final String move = moveResult.getResponse().getContentAsString();

        calculateResult(gameId, move, move, GameResults.DRAW.name());
    }

    private void calculateResult(String gameId, String move, String ourMove, String expectedResult) throws Exception {
        final MvcResult gameResult = mvc.perform(MockMvcRequestBuilders.get(String.format("/calculate/%s/%s/%s", gameId, ourMove, move))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final String result = gameResult.getResponse().getContentAsString();

        assertEquals(String.format("%s to %s should be %s", move, ourMove, expectedResult), expectedResult, result);
    }

    private String generateResponseMove(final GameType gameType,
                                        final String move,
                                        final List<String> availableMoves,
                                        final boolean toWin) {
        for (final String resultMove : availableMoves) {
            final GameResults result = gameType.calculateResult(resultMove, move);
            switch (result) {
                case FIRST_WIN:
                    if (toWin) return resultMove;
                    break;
                case SECOND_WIN:
                    if (!toWin) return resultMove;
                    break;
            }
        }
        throw new IllegalArgumentException(String.format("Cannot find response for '%s' for such goal: %s",
                move, toWin ? "WIN" : "LOST"));
    }
}
