package pl.go.volley.govolley.league;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends CrudRepository<League, Long> {
}
