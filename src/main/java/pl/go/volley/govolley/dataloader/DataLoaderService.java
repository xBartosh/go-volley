package pl.go.volley.govolley.dataloader;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.league.LeagueRepository;
import pl.go.volley.govolley.player.Player;
import pl.go.volley.govolley.team.Team;
import pl.go.volley.govolley.team.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DataLoaderService {

    private final DataFileLoader dataFileLoader;
    private final LeagueRepository leagueRepository;

    private final TeamRepository teamRepository;

    public DataLoaderService(DataFileLoader dataFileLoader, LeagueRepository leagueRepository, TeamRepository teamRepository) {
        this.dataFileLoader = dataFileLoader;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
    }

    public List<League> readLeaguesFromFile(){
        List<League> leagues = dataFileLoader.readLeaguesFromFile();
        leagueRepository.saveAll(leagues);
        return leagues;
    }

    @Transactional
    public List<Team> readPlayersFromFile(){
        Map<String, List<Player>> playersForTeam = dataFileLoader.readPlayersFromFile();
        List<Team> teamsUpdated = new ArrayList<>();
        playersForTeam.forEach((teamName, players) -> {
            Optional<Team> team = teamRepository.findTeamByNameIgnoreCase(teamName);
            team.ifPresent(t -> {
                players.forEach(player -> player.setTeam(t));
                t.setPlayers(players);
                teamsUpdated.add(t);
            });
        });

        return teamsUpdated;
    }
}
