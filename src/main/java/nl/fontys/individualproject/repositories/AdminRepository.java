package nl.fontys.individualproject.repositories;

import lombok.Generated;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.repositories.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Generated
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Integer> {
    @Modifying
    @Query("UPDATE AdminEntity c SET c.gmail = :gmail, c.username = :username, c.dateOfBirth = :dateOfBirth, c.playingLocation = :playingLocation WHERE c.id = :id")
    void updateAdmin(@Param("id") int id,
                     @Param("gmail") String gmail,
                     @Param("username") String username,
                     @Param("dateOfBirth") LocalDate dateOfBirth,
                     @Param("playingLocation") PlayingLocation playingLocation);
}
