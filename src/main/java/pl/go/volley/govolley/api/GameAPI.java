package pl.go.volley.govolley.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.go.volley.govolley.exception.GameNotFoundException;
import pl.go.volley.govolley.game.GameService;

@RestController
@RequestMapping("/api/game")
public class GameAPI {
    private final GameService gameService;

    public GameAPI(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateGame(@RequestParam Long gameId,
                                             @RequestParam Integer teamAScore,
                                             @RequestParam Integer teamBScore,
                                             @RequestParam Integer teamASmallPoints,
                                             @RequestParam Integer teamBSmallPoints) {
        try {
            gameService.updateGameScore(gameId, teamAScore, teamBScore, teamASmallPoints, teamBSmallPoints);
            return ResponseEntity.ok().build();
        } catch (GameNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
