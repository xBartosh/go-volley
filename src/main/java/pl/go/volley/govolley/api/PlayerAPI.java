package pl.go.volley.govolley.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.go.volley.govolley.player.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerAPI {
    private final PlayerService playerService;

    public PlayerAPI(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addPlayer(@RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam String teamName){
        boolean added = playerService.addPlayerToTeam(firstName, lastName, teamName);

        return added ?
                ResponseEntity.ok().body(true) :
                ResponseEntity.badRequest().body(false);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deletePlayer(@RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestParam String teamName){
        boolean deleted = playerService.deletePlayerFromTeam(firstName, lastName, teamName);

        return deleted ?
                ResponseEntity.ok().body(true) :
                ResponseEntity.badRequest().body(false);
    }
}
