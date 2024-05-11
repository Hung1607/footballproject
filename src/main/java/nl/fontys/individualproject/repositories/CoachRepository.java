package nl.fontys.individualproject.repositories;

import lombok.Generated;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.repositories.Entity.CoachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Generated
public interface CoachRepository extends JpaRepository<CoachEntity,Integer> {
    @Modifying
    @Query("UPDATE CoachEntity c SET c.gmail = :gmail, c.username = :username, c.dateOfBirth = :dateOfBirth, c.playingLocation = :playingLocation, c.description = :description,c.experience = :experience WHERE c.id = :id")
    void updateCoach(@Param("id") int id,
                      @Param("gmail") String gmail,
                      @Param("username") String username,
                      @Param("dateOfBirth") LocalDate dateOfBirth,
                      @Param("playingLocation") PlayingLocation playingLocation,
                      @Param("description") String description,
                      @Param("experience") String experience);

    @Modifying
    @Query("UPDATE CoachEntity c SET c.status = :status WHERE c.id = :id")
    void updateCoachStatus(@Param("id") int id,
                           @Param("status") String status);

}
