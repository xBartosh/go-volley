package pl.go.volley.govolley.team;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.league.LeagueRepository;

import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public TeamService(TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public Optional<Team> findTeamByDivisionAndName(Integer division, String teamName){
        Optional<League> league = leagueRepository.findLeagueByDivision(division);
        return teamRepository.findTeamByLeagueAndNameIgnoreCase(league.get(), teamName);
    }

    @Transactional
    public Optional<String> changeTeamName(String teamNameNow, String teamNameChanged){
        Optional<Team> team = teamRepository.findTeamByNameIgnoreCase(teamNameNow);
        if(team.isEmpty()){
            return Optional.of("Nie ma takiej drużyny!");
        }

        team.get().setName(teamNameChanged);
        return Optional.empty();
    }
}
