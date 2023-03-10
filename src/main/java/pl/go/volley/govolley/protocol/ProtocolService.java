package pl.go.volley.govolley.protocol;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.go.volley.govolley.dto.LeagueDateDTO;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.game.GameRepository;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.league.LeagueRepository;
import pl.go.volley.govolley.protocol.generator.ProtocolGenerator;
import pl.go.volley.govolley.utils.TimestampUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProtocolService {
    private final GameRepository gameRepository;
    private final LeagueRepository leagueRepository;

    private final ProtocolGenerator protocolGenerator;

    public ProtocolService(GameRepository gameRepository, LeagueRepository leagueRepository, ProtocolGenerator protocolGenerator) {
        this.gameRepository = gameRepository;
        this.leagueRepository = leagueRepository;
        this.protocolGenerator = protocolGenerator;
    }

    @Cacheable("protocol-data")
    public List<RoundProtocolData> getLeagueProtocolData() {
        List<League> leagues = IterableUtils.toList(leagueRepository.findAll());
        List<Integer> rounds = gameRepository.findAllRoundValues();

        return rounds.parallelStream().map(round -> {
            Map<LeagueDateDTO, List<Game>> gamesForDivision = new HashMap<>();
            leagues.parallelStream().forEach(league -> {
                List<Game> gamesForRoundAndLeague = gameRepository.findAllByLeagueAndRound(league, round);
                String dateTime = TimestampUtils.convertTimestampToFormattedString(gamesForRoundAndLeague.get(0).getDate());
                gamesForDivision.put(new LeagueDateDTO(dateTime, league.getDivision()), gamesForRoundAndLeague);
            });

            return new RoundProtocolData(round, gamesForDivision.entrySet().stream()
                    .sorted(Comparator.comparing(entry -> entry.getKey().getDivision()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    )));
        }).collect(Collectors.toList());
    }

    public Optional<File> createMergedProtocolForGames(List<Long> gameIds) throws Exception {
        return protocolGenerator.generateMergedProtocolForGames(gameRepository.findAllByIdIn(gameIds));
    }
}
