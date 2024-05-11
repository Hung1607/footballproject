package nl.fontys.individualproject.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Getter
@Setter
public class Coach extends User{
    private String experience;
    private String description;
    private String status;

    @Builder
    public Coach(int id, String gmail, String username, LocalDate dateOfBirth,
                 PlayingLocation playingLocation, String experience, String description,String status) {
        super(id, gmail, username, dateOfBirth, playingLocation);
        this.experience = experience;
        this.description = description;
        this.status = status;
    }
}
