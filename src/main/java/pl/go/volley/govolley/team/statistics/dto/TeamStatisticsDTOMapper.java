package pl.go.volley.govolley.team.statistics.dto;

import pl.go.volley.govolley.team.statistics.TeamStatistics;

public class TeamStatisticsDTOMapper {
    public static TeamStatisticsDTO mapTeamStatisticsToDTO(TeamStatistics teamStatistics){
        return TeamStatisticsDTO.builder()
                .teamName(teamStatistics.getTeam().getName())
                .gamesPlayed(teamStatistics.getGamesPlayed())
                .points(teamStatistics.getPoints())
                .wins(teamStatistics.getWins())
                .loses(teamStatistics.getLoses())
                .setsWon(Double.valueOf(teamStatistics.getSetsWon()))
                .setsLost(Double.valueOf(teamStatistics.getSetsLost()))
                .smallPointsWon(Double.valueOf(teamStatistics.getSmallPointsWon()))
                .smallPointsLost(Double.valueOf(teamStatistics.getSmallPointsLost()))
                .build();
    }
}
