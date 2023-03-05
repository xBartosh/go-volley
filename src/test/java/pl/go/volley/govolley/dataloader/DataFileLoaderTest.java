package pl.go.volley.govolley.dataloader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.player.Player;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataFileLoaderTest {

    private final DataFileLoader dataFileLoader = new DataFileLoader();

    @Test
    public void shouldReturnListOfLeagues(){
        List<League> leagues = dataFileLoader.readLeaguesFromFile();
        Assertions.assertEquals(5, leagues.size());
        leagues.forEach(league -> {
            assertEquals(league.getTeams().size(), 6);
            assertEquals(league.getGames().size(), 30);
        });
    }

    @Test
    public void shouldReturnMapOfTeamsAndPlayers(){
        Map<String, List<Player>> playersForTeam = dataFileLoader.readPlayersFromFile();
        Assertions.assertEquals(30, playersForTeam.size());
    }

}