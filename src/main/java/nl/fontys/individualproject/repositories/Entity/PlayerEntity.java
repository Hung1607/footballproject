package nl.fontys.individualproject.repositories.Entity;

import jakarta.persistence.*;
import lombok.*;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;

import java.time.LocalDate;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@Table(name="player")
public class PlayerEntity extends UserEntity {

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToMany(mappedBy = "players")
    private List<MatchEntity> matches;

    @Builder
    public PlayerEntity(int id, String gmail, String username, String password, LocalDate dateOfBirth,
                        PlayingLocation playingLocation, Position position) {
        super(id, gmail, username, password, dateOfBirth, playingLocation);
        this.position = position;
    }
}
