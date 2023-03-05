package pl.go.volley.govolley.player;

import org.springframework.stereotype.Service;
import pl.go.volley.govolley.team.Team;
import pl.go.volley.govolley.team.TeamRepository;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public boolean addPlayerToTeam(String firstName, String lastName, String teamName) {
        Optional<Team> team = teamRepository.findTeamByNameIgnoreCase(teamName);
        if (team.isPresent()) {
            Player player = Player.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .team(team.get())
                    .build();
            team.get().getPlayers().add(player);
            teamRepository.save(team.get());

            return true;
        }

        return false;
    }

    public boolean deletePlayerFromTeam(String firstName, String lastName, String teamName) {
        Optional<Team> team = teamRepository.findTeamByNameIgnoreCase(teamName);
        if (team.isPresent()) {
            Optional<Player> player = playerRepository.findPlayerByFirstNameAndLastNameAndTeam(firstName, lastName, team.get());
            if (player.isPresent()) {
                playerRepository.delete(player.get());
                return true;
            }
        }

        return false;
    }
}
