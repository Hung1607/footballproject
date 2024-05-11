package nl.fontys.individualproject.repositories.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="match")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @OneToOne
    @JoinColumn(name="stadium_id")
    private StadiumEntity stadium;

    @NotNull
    @OneToOne
    @JoinColumn(name="coach_id")
    private CoachEntity coach;

    @NotNull
    @Column(name="date")
    private LocalDate date;

    @NotNull
    @Column(name="time")
    private LocalTime time;

    @NotNull
    @Column(name="time_end")
    private LocalTime time_end;

    @ManyToMany
    @JoinTable(
            name = "match_player",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<PlayerEntity> players;

}
