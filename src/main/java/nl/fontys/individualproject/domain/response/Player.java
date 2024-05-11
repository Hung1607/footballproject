package nl.fontys.individualproject.domain.response;

import lombok.*;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;

import java.time.LocalDate;


@Getter
@Setter
public class Player extends User {
    private Position position;

    @Builder
    public Player(int id, String gmail, String username, LocalDate dateOfBirth,
                  PlayingLocation playingLocation, Position position) {
        super(id, gmail, username, dateOfBirth, playingLocation);
        this.position = position;
    }
}