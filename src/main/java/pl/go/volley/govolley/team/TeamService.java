package pl.go.volley.govolley.team;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.league.LeagueRepository;
import pl.go.volley.govolley.team.statistics.TeamStatistics;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public TeamService(TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<Team> findAllTeams(){
        return IterableUtils.toList(teamRepository.findAll());
    }

    public Optional<Team> findTeamById(Long teamId){
        return teamRepository.findById(teamId);
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

    @Transactional
    public List<TeamStatistics> createTeamStatistics(){
        List<Team> teams = findAllTeams();
        teams.forEach(team -> team.setTeamStatistics(new TeamStatistics(team)));

        return teams.stream()
                .map(Team::getTeamStatistics)
                .collect(Collectors.toList());
    }
}
