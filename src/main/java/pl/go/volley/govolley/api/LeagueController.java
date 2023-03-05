package pl.go.volley.govolley.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.league.LeagueService;
import pl.go.volley.govolley.team.Team;
import pl.go.volley.govolley.team.TeamService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/league")
public class LeagueController {

    private final LeagueService leagueService;
    private final TeamService teamService;

    public LeagueController(LeagueService leagueService, TeamService teamService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
    }

    @GetMapping
    public String homePage(Model model) {
        Optional<List<League>> leagues = leagueService.findAllLeagues();
        model.addAttribute("leagues", leagues.get());
        return "index";
    }

    @GetMapping("/{division}")
    public String getLeague(@PathVariable Integer division,
                            Model model) {
        Optional<League> league = leagueService.findLeagueByDivision(division);
        if (league.isEmpty()) {
            return "error";
        }
        model.addAttribute("league", league.get());
        return "league";
    }

    @GetMapping("/{division}/{name}")
    public String getTeamForLeague(@PathVariable Integer division,
                                   @PathVariable String name,
                                   Model model) {
        Optional<Team> team = teamService.findTeamByDivisionAndName(division, name);
        if (team.isEmpty()) {
            return "error";
        }
        model.addAttribute("team", team.get());
        return "team";
    }

}
