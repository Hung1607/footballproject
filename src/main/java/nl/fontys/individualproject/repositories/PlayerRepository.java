package nl.fontys.individualproject.repositories;

import lombok.Generated;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.Position;
import nl.fontys.individualproject.repositories.Entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Generated
public interface PlayerRepository extends JpaRepository<PlayerEntity,Integer> {
    @Modifying
    @Query("UPDATE PlayerEntity p SET p.gmail = :gmail, p.username = :username, p.dateOfBirth = :dateOfBirth, p.playingLocation = :playingLocation, p.position = :position WHERE p.id = :id")
    void updatePlayer(@Param("id") int id,
                      @Param("gmail") String gmail,
                      @Param("username") String username,
                      @Param("dateOfBirth") LocalDate dateOfBirth,
                      @Param("playingLocation") PlayingLocation playingLocation,
                      @Param("position") Position position);
}
