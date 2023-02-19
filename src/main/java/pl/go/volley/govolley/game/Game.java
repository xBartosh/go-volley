package pl.go.volley.govolley.game;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.go.volley.govolley.league.League;
import pl.go.volley.govolley.team.Team;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private League league;


}
