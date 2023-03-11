package pl.go.volley.govolley.team.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.go.volley.govolley.team.Team;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TeamStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer gamesPlayed;
    private Integer points;
    private Integer wins;
    private Integer loses;
    private Integer setsWon;
    private Integer setsLost;
    private Integer smallPointsWon;
    private Integer smallPointsLost;

    @OneToOne
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    public TeamStatistics(Team team) {
        this.team = team;
    }
}
