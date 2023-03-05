package pl.go.volley.govolley.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.go.volley.govolley.team.TeamService;

import java.util.Optional;

@Controller
@RequestMapping("/api/team")
public class TeamAPI {
    private final TeamService teamService;

    public TeamAPI(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/changeTeamName")
    public ResponseEntity<String> changeTeamName(@RequestParam String teamNameNow,
                                                 @RequestParam String teamNameChanged){
        Optional<String> errorMessage = teamService.changeTeamName(teamNameNow, teamNameChanged);

        return errorMessage
                .map(message -> ResponseEntity.badRequest().body(message))
                .orElse(ResponseEntity.ok().build());
    }
}
