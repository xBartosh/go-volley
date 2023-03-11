package pl.go.volley.govolley.league;

import com.google.common.collect.Lists;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.go.volley.govolley.league.dto.LeagueStatisticsDTO;
import pl.go.volley.govolley.team.statistics.TeamStatisticsService;
import pl.go.volley.govolley.team.statistics.dto.TeamStatisticsDTO;
import pl.go.volley.govolley.team.statistics.dto.TeamStatisticsDTOComparator;
import pl.go.volley.govolley.team.statistics.dto.TeamStatisticsDTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamStatisticsService teamStatisticsService;

    public LeagueService(LeagueRepository leagueRepository, TeamStatisticsService teamStatisticsService) {
        this.leagueRepository = leagueRepository;
        this.teamStatisticsService = teamStatisticsService;
    }

    public Optional<List<League>> findAllLeagues() {
        return Optional.of(Lists.newArrayList(leagueRepository.findAll()));
    }

    public Optional<List<League>> findAllLeaguesOrderByDivision() {
        return leagueRepository.findAll(Sort.by(Sort.Direction.ASC, "division"));
    }

    public Optional<League> findLeagueByDivision(Integer division) {
        return leagueRepository.findLeagueByDivision(division);
    }

    @Cacheable("standings")
    public Optional<LeagueStatisticsDTO> findLeagueByDivisionWithTeamStatistics(Integer division) {
        Optional<League> league = findLeagueByDivision(division);
        if (league.isEmpty()) {
            return Optional.empty();
        }


        List<TeamStatisticsDTO> teamStatisticsDTOS = league.get().getTeams().stream()
                .map(team -> TeamStatisticsDTOMapper.mapTeamStatisticsToDTO(teamStatisticsService.findAndSaveStatisticsForTeam(team)))
                .sorted(new TeamStatisticsDTOComparator())
                .toList();

        LeagueStatisticsDTO leagueStatisticsDTOS = new LeagueStatisticsDTO(division, teamStatisticsDTOS);

        return Optional.of(leagueStatisticsDTOS);
    }
}
