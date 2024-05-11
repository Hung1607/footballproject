package nl.fontys.individualproject.repositories;

import lombok.Generated;
import nl.fontys.individualproject.repositories.Entity.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Generated
public interface MatchRepository extends JpaRepository<MatchEntity,Integer> {
    @Query("SELECT m FROM MatchEntity m WHERE m.date = :date")
    List<MatchEntity> getMatchEntitiesByDate(@Param ("date") LocalDate date);

//    @Query("SELECT COUNT (m) FROM MatchEntity m where m.coach.id = :id AND m.date BETWEEN :startDate AND :endDate")
//    int getTotalMatchByCoachId(@Param ("id") int id,
//                               @Param ("startDate") LocalDate dateStart,
//                               @Param ("endDate") LocalDate dateEnd);

    @Query("SELECT c.username,m.coach.id, COUNT(m) FROM MatchEntity m join m.coach c WHERE m.date BETWEEN :startDate AND :endDate GROUP BY c.username,m.coach.id")
    List<Object[]> getTotalMatchByAllCoach(
            @Param("startDate") LocalDate dateStart,
            @Param("endDate") LocalDate dateEnd);
}
