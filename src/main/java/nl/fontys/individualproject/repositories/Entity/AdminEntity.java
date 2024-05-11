package nl.fontys.individualproject.repositories.Entity;
import  jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class AdminEntity extends UserEntity {
    @Builder
    public AdminEntity(int id,String gmail, String username, String password, LocalDate dateOfBirth,PlayingLocation playingLocation) {
        super(id, gmail, username,password,dateOfBirth,playingLocation);
    }
}
