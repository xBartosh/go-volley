package pl.go.volley.govolley.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.go.volley.govolley.dataloader.DataLoaderService;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.team.Team;

import java.util.List;

@RestController
@RequestMapping("/api/dataloader")
public class DataLoaderAPI {

    private final DataLoaderService dataLoaderService;

    public DataLoaderAPI(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    @PostMapping("/league")
    public ResponseEntity<List<League>> loadLeagueData(){
        List<League> leagues = dataLoaderService.readLeaguesFromFile();

        return ResponseEntity.ok().body(leagues);
    }

    @PostMapping("/players")
    public ResponseEntity<List<Team>> loadPlayersData(){
        List<Team> teams = dataLoaderService.readPlayersFromFile();

        return ResponseEntity.ok().body(teams);
    }
}
