package pl.go.volley.govolley.league;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public Optional<List<League>> findAllLeagues(){
        return Optional.of(Lists.newArrayList(leagueRepository.findAll()));
    }
}
