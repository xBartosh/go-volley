package pl.go.volley.govolley.protocol.generator;

import org.junit.jupiter.api.Test;
import pl.go.volley.govolley.exception.CannotCopyFileException;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.player.Player;
import pl.go.volley.govolley.team.Team;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class ProtocolGeneratorTest {

    private final ProtocolGenerator protocolGenerator = new ProtocolGenerator();

    @Test
    public void shouldReturnGoodFile() throws IOException {
        Team teamA = new Team("Rapapara");
        teamA.setPlayers(List.of(new Player("Michał", "Szpakowski"),
                new Player("Żelisław", "Żeżyński"),
                new Player("Dominik", "Kielar"),
                new Player("Dominik", "Kielar"),
                new Player("Dominik", "Kielar"),
                new Player("Dominik", "Kielar"),
                new Player("Dominik", "Kielar")));

        Team teamB = new Team("JAZZY NIEPOŁOMICE II");
        teamB.setPlayers(List.of(new Player("Michał", "Szpakowski"),
                new Player("Żelisław", "Żeżyński"),
                new Player("Kierownik", "Kielar"),
                new Player("Julia", "Krajewska")));

        Game game = Game.builder()
                .teamA(teamA)
                .teamB(teamB)
                .round(3)
                .build();
        protocolGenerator.generateMergedProtocolForGames(List.of(game));
    }

    @Test
    public void shouldReturnFiles() throws CannotCopyFileException {

    }

}