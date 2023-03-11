package pl.go.volley.govolley.team.statistics;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.game.GameService;
import pl.go.volley.govolley.team.Team;
import pl.go.volley.govolley.team.TeamService;

import java.util.List;

@Repository
public class TeamStatisticsService {
    private final TeamStatisticsRepository teamStatisticsRepository;
    private final GameService gameService;

    public TeamStatisticsService(TeamStatisticsRepository teamStatisticsRepository, GameService gameService) {
        this.teamStatisticsRepository = teamStatisticsRepository;
        this.gameService = gameService;
    }

    @Transactional
    public TeamStatistics findAndSaveStatisticsForTeam(Team team) {
        TeamStatistics teamStatistics = teamStatisticsRepository.findByTeam(team);
        List<Game> gamesPlayedByTeam = gameService.findAllGamesWithScoreByTeam(team);

        int gamesPlayed = gamesPlayedByTeam.size();
        int points = 0;
        int wins = 0;
        int loses = 0;
        int setsWon = 0;
        int setsLost = 0;
        int smallPointsWon = 0;
        int smallPointsLost = 0;

        for (Game game : gamesPlayedByTeam) {
            Integer teamAScore = game.getTeamAScore();
            Integer teamBScore = game.getTeamBScore();

            if (game.getTeamA().getName().equalsIgnoreCase(team.getName())) {
                setsWon += teamAScore;
                setsLost += teamBScore;
                smallPointsWon += game.getTeamASmallPoints();
                smallPointsLost += game.getTeamBSmallPoints();

                if (teamAScore > teamBScore) {
                    points += (teamBScore == 2) ? 2 : 3;
                    wins++;
                } else {
                    points += (teamAScore == 2) ? 1 : 0;
                    loses++;
                }
            } else {
                setsWon += teamBScore;
                setsLost += teamAScore;
                smallPointsWon += game.getTeamBSmallPoints();
                smallPointsLost += game.getTeamASmallPoints();

                if (teamBScore > teamAScore) {
                    points += (teamAScore == 2) ? 2 : 3;
                    wins += 1;
                } else {
                    points += (teamBScore == 2) ? 1 : 0;
                    loses += 1;
                }
            }
        }

        teamStatistics.setGamesPlayed(gamesPlayed);
        teamStatistics.setPoints(points);
        teamStatistics.setWins(wins);
        teamStatistics.setLoses(loses);
        teamStatistics.setSetsWon(setsWon);
        teamStatistics.setSetsLost(setsLost);
        teamStatistics.setSmallPointsWon(smallPointsWon);
        teamStatistics.setSmallPointsLost(smallPointsLost);

        teamStatisticsRepository.save(teamStatistics);

        return teamStatistics;
    }
}
