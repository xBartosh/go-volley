package pl.go.volley.govolley.league.dto;

import lombok.*;
import pl.go.volley.govolley.team.statistics.dto.TeamStatisticsDTO;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LeagueStatisticsDTO {
    private Integer division;
    private List<TeamStatisticsDTO> teamsStatistics;
}
