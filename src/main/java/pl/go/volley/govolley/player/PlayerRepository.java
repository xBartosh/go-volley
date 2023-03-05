package pl.go.volley.govolley.player;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.go.volley.govolley.team.Team;

import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findPlayerByFirstNameAndLastNameAndTeam(String firstName, String lastName, Team team);
}
