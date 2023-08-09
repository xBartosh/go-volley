package pl.go.volley.govolley.dataloader;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.player.Player;
import pl.go.volley.govolley.team.Team;
import pl.go.volley.govolley.team.statistics.TeamStatistics;
import pl.go.volley.govolley.utils.TimestampUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DataFileLoader {
    private static final String TIMETABLE_FILE_PATH = "/static/leagues-data/timetable.txt";
    private static final String PLAYER_FILE_PATH = "/static/leagues-data/players.txt";

    public List<League> readLeaguesFromFile() {
        List<League> leagues = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(DataFileLoader.class.getResourceAsStream(TIMETABLE_FILE_PATH))))) {
            String line;
            Integer round = null;
            Timestamp date = null;
            List<Game> games = null;
            List<Team> teams = null;
            League league = null;
            while ((line = br.readLine()) != null) {
                if (league != null && !leagues.contains(league)) {
                    league.setGames(games);
                    league.setTeams(teams);
                    leagues.add(league);
                }
                if (line.matches(".*Liga.*")) {
                    games = new ArrayList<>();
                    teams = new ArrayList<>();
                    league = new League();
                    String[] leagueInfo = line.split("\\s+");
                    league.setDivision(Integer.parseInt(leagueInfo[1]));
                } else if (line.matches(".*KOLEJKA.*")) {
                    String[] roundInfo = line.split("\\s+");
                    round = Integer.parseInt(roundInfo[1]);
                    date = TimestampUtils.createTimestampFromDateAndHour(roundInfo[2], roundInfo[3].split("\\.")[1]);
                } else if (!line.isBlank()) {
                    String[] gameInfo = line.split("\\s+:\\s+");
                    String teamAName = gameInfo[0].trim();
                    String teamBName = gameInfo[1].trim();
                    Team teamA = getTeam(teamAName, teams, league);
                    Team teamB = getTeam(teamBName, teams, league);

                    games.add(new Game(teamA, teamB, date, round, league));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return leagues;
    }

    public Map<String, List<Player>> readPlayersFromFile() {
        Map<String, List<Player>> playersForTeam = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(DataFileLoader.class.getResourceAsStream(PLAYER_FILE_PATH))))) {
            String line;
            String teamName;
            List<Player> players = null;
            while ((line = br.readLine()) != null) {
                if (line.matches(".*Drużyna.*")) {
                    players = new ArrayList<>();
                    teamName = line.split("Drużyna\\s+")[1];
                    playersForTeam.put(teamName, players);
                } else {
                    String[] fullname = line.split("\\s+");
                    players.add(new Player(fullname[0].trim(), fullname[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return playersForTeam;
    }


    private Team getTeam(String teamName, List<Team> teams, League league) {
        Team team;
        if (teams.stream().noneMatch(t -> t.getName().equalsIgnoreCase(teamName))) {
            team = new Team(teamName, league);
            team.setTeamStatistics(new TeamStatistics(team));
            teams.add(team);
        } else {
            team = teams.stream().filter(t -> t.getName().equalsIgnoreCase(teamName)).findFirst().get();
        }

        return team;
    }
}
