package pl.go.volley.govolley.team.statistics.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import pl.go.volley.govolley.team.statistics.TeamStatistics;

import java.util.Comparator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TeamStatisticsDTO {
    private String teamName;
    private Integer gamesPlayed;
    private Integer points;
    private Integer wins;
    private Integer loses;
    private Double setsWon;
    private Double setsLost;
    private Double smallPointsWon;
    private Double smallPointsLost;
}
