package nl.fontys.individualproject.repositories.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.SurfaceType;
import org.hibernate.validator.constraints.Length;

@Builder
@Entity
@Getter
@Table(name="[stadium]")
@AllArgsConstructor
@NoArgsConstructor
public class StadiumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Length(min= 2)
    @Column(name="name")
    private String name;

    @NotBlank
    @Length(min= 5)
    @Column(name="address")
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="location")
    private PlayingLocation playingLocation;

    @NotNull
    @Column(name="capacity")
    private int capacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="surface_type")
    private SurfaceType surfaceType;
}
