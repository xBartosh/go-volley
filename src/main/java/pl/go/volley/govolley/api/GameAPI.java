package pl.go.volley.govolley.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger LOGGER = LoggerFactory.getLogger(GameAPI.class);
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
            LOGGER.info("Successfully update game score with id: {}", gameId);
            return ResponseEntity.ok().build();
        } catch (GameNotFoundException e) {
            LOGGER.error("Could not update game score with id: {}. Error message: {}", gameId, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
