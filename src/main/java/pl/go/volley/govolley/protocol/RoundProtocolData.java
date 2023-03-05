package pl.go.volley.govolley.protocol;

import lombok.*;
import pl.go.volley.govolley.dto.LeagueDateDTO;
import pl.go.volley.govolley.game.Game;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class RoundProtocolData {
    private Integer round;
    private Map<LeagueDateDTO, List<Game>> gamesForDivision;
}
