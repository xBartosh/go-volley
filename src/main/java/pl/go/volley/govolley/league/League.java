package pl.go.volley.govolley.league;

import jakarta.persistence.*;
import lombok.*;
import pl.go.volley.govolley.team.Team;
import pl.go.volley.govolley.game.Game;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "league")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer division;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "league")
    private List<Team> teams = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "league")
    private List<Game> games = new ArrayList<>();

    public League(Integer division) {
        this.division = division;
    }
}
