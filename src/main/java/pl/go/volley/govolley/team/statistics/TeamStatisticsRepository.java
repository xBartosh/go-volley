package pl.go.volley.govolley.team.statistics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.go.volley.govolley.team.Team;

@Repository
public interface TeamStatisticsRepository extends CrudRepository<TeamStatistics, Long> {
    TeamStatistics findByTeam(Team team);
}
