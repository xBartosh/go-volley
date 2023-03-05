package pl.go.volley.govolley.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.go.volley.govolley.league.League;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findAllByLeagueAndRound(League league, Integer round);

    List<Game> findAllByIdIn(List<Long> ids);

    @Query("SELECT DISTINCT g.round FROM Game g ORDER BY g.round ASC")
    List<Integer> findAllRoundValues();
}
