package pl.go.volley.govolley.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.player.Player;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "league_id")
    @JsonIgnore
    private League league;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teamA")
    @JsonIgnore
    private List<Game> gamesA = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teamB")
    @JsonIgnore
    private List<Game> gamesB;

    public Team(String name){
        this.name = name;
    }


}
