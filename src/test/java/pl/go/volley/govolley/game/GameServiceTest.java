package pl.go.volley.govolley.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.go.volley.govolley.exception.GameNotFoundException;
import pl.go.volley.govolley.team.statistics.TeamStatistics;
import pl.go.volley.govolley.team.statistics.TeamStatisticsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class GameServiceTest {
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    public void init(){
        gameRepository = Mockito.mock(GameRepository.class);
        gameService = new GameService(gameRepository);
    }

    @Test
    public void shouldUpdateGame() throws GameNotFoundException {
        // given
        Long gameId = 1L;
        Game game = new Game();

        // when
        Mockito.when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        Game updatedGame = gameService.updateGameScore(gameId, 3, 75, 0, 55);

        // then
        assertEquals(3, updatedGame.getTeamAScore());
        assertEquals(75, updatedGame.getTeamASmallPoints());
        assertEquals(0, updatedGame.getTeamBScore());
        assertEquals(55, updatedGame.getTeamBSmallPoints());
    }

    @Test
    public void shouldThrowException() {
        // given
        Long gameId = 2L;

        // when
        Mockito.when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // then
        GameNotFoundException gameNotFoundException = assertThrows(GameNotFoundException.class, () -> gameService.updateGameScore(gameId, 3, 75, 0, 55));
        assertEquals("Game not found!", gameNotFoundException.getMessage());
    }
}