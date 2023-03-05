package pl.go.volley.govolley.game;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.team.Team;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private Team teamA;
    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private Team teamB;
    private Timestamp date;
    private Integer round;
    @ManyToOne
    @JoinColumn(name = "league_id")
    @JsonIgnore
    private League league;

    public Game(Team teamA, Team teamB, Timestamp date, Integer round, League league) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.round = round;
        this.league = league;
    }


}
