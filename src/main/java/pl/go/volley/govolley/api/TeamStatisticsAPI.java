package pl.go.volley.govolley.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.go.volley.govolley.team.TeamService;
import pl.go.volley.govolley.team.statistics.TeamStatistics;

import java.util.List;

@RestController
@RequestMapping("/api/team-statistics")
public class TeamStatisticsAPI {

    private final TeamService teamService;

    public TeamStatisticsAPI(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/initial-create")
    public List<TeamStatistics> createTeamStatistics(){
        return teamService.createTeamStatistics();
    }
}
