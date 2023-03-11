package pl.go.volley.govolley.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.team.Team;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findAllByLeagueAndRound(League league, Integer round);

    List<Game> findAllByIdIn(List<Long> ids);

    @Query("SELECT g FROM Game g WHERE g.teamAScore IS NOT NULL AND g.teamBScore IS NOT NULL AND (g.teamA = :team OR g.teamB = :team)")
    List<Game> findAllByTeamAndScoreIsNotNull(@Param("team") Team team);

    @Query("SELECT DISTINCT g.round FROM Game g ORDER BY g.round ASC")
    List<Integer> findAllRoundValues();
}
