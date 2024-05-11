package nl.fontys.individualproject.domain.request;

import lombok.Builder;
import lombok.Getter;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;

import java.time.LocalDate;

@Builder
@Getter
public class PlayerRequest {
    private String gmail;
    private Position position;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private PlayingLocation playingLocation;
}
