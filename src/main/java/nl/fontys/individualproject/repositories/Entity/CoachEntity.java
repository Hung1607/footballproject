package nl.fontys.individualproject.repositories.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name="coach")
public class CoachEntity extends UserEntity {
    @NotBlank
    @Column(name="experience")
    private String experience;

    @NotBlank
    @Column(name="description")
    private String description;

    @NotBlank
    @Column(name="status")
    private String status;

    @Builder
    public CoachEntity(int id, String gmail, String username,String password, LocalDate dateOfBirth,
                 PlayingLocation playingLocation, String experience, String description,String status) {
        super(id, gmail, username,password,dateOfBirth,playingLocation);
        this.experience = experience;
        this.description = description;
        this.status = status;
    }
}
