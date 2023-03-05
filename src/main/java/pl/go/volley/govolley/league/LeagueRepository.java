package pl.go.volley.govolley.league;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeagueRepository extends CrudRepository<League, Long> {
    Optional<List<League>> findAll(Sort sort);
    Optional<League> findLeagueByDivision(Integer division);
}
