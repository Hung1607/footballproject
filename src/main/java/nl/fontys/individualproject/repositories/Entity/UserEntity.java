package nl.fontys.individualproject.repositories.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.individualproject.domain.enume.PlayingLocation;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="[user]")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Column(name="gmail")
    private String gmail;

    @NotBlank
    @Column(name="username")
    private String username;

    @NotBlank
    @Column(name="password")
    private String password;

    @NotNull
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name="location")
    @Enumerated(EnumType.STRING)
    private PlayingLocation playingLocation;
}
