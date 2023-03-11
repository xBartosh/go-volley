package pl.go.volley.govolley.team.statistics.dto;

import java.util.Comparator;

public class TeamStatisticsDTOComparator implements Comparator<TeamStatisticsDTO> {
    @Override
    public int compare(TeamStatisticsDTO team1, TeamStatisticsDTO team2) {
        // First, compare by points
        int result = team2.getPoints().compareTo(team1.getPoints());
        if (result != 0) {
            return result;
        }

        // Then, compare by wins
        result = team2.getWins().compareTo(team1.getWins());
        if (result != 0) {
            return result;
        }

        // Next, compare by set ratio
        double setRatio1 = (double) team1.getSetsWon() / team1.getSetsLost();
        double setRatio2 = (double) team2.getSetsWon() / team2.getSetsLost();
        result = Double.compare(setRatio2, setRatio1);
        if (result != 0) {
            return result;
        }

        // Finally, compare by small points ratio
        double smallPointsRatio1 = (double) team1.getSmallPointsWon() / team1.getSmallPointsLost();
        double smallPointsRatio2 = (double) team2.getSmallPointsWon() / team2.getSmallPointsLost();
        result = Double.compare(smallPointsRatio2, smallPointsRatio1);
        return result;
    }
}

