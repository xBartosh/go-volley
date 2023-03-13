package pl.go.volley.govolley.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.go.volley.govolley.exception.GameNotFoundException;
import pl.go.volley.govolley.team.Team;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict("protocol-data"),
            @CacheEvict(value = "standings", key = "#result.league.division")
    })
    public Game updateGameScore(Long gameId,
                           Integer teamAScore, Integer teamBScore,
                           Integer teamASmallPoints, Integer teamBSmallPoints) throws GameNotFoundException {
        LOGGER.info("Going to update game with id: {}, score: {} : {}, small points: {} : {}", gameId, teamAScore, teamBScore, teamASmallPoints, teamBSmallPoints);
        Optional<Game> gameToUpdate = gameRepository.findById(gameId);
        gameToUpdate.orElseThrow(GameNotFoundException::new);

        Game game = gameToUpdate.get();
        game.setTeamAScore(teamAScore);
        game.setTeamASmallPoints(teamASmallPoints);
        game.setTeamBScore(teamBScore);
        game.setTeamBSmallPoints(teamBSmallPoints);

        return game;
    }

    public List<Game> findAllGamesWithScoreByTeam(Team team){
        return gameRepository.findAllByTeamAndScoreIsNotNull(team);
    }
}
