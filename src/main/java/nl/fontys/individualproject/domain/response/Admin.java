package nl.fontys.individualproject.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Getter
@Setter
public class Admin extends User{
    @Builder
    public Admin(int id, String gmail, String username, LocalDate dateOfBirth,
                 PlayingLocation playingLocation) {
        super(id, gmail, username, dateOfBirth, playingLocation);
    }
}