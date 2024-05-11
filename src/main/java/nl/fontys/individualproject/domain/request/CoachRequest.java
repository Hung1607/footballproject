package nl.fontys.individualproject.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class CoachRequest {
    private String gmail;
    private String username;
    private String password;
    private String description;
    private LocalDate dateOfBirth;
    private PlayingLocation playingLocation;
    private String experience;
}
