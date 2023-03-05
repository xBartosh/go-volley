package pl.go.volley.govolley.team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.go.volley.govolley.league.League;

import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    Optional<Team> findTeamByLeagueAndNameIgnoreCase(League league, String name);
    Optional<Team> findTeamByNameIgnoreCase(String name);
}
