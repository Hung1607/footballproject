package nl.fontys.individualproject.repositories;

import lombok.Generated;
import nl.fontys.individualproject.repositories.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Generated
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    boolean existsByGmail(String gmail);
    boolean existsByUsername(String username);
    UserEntity getByUsername(String username);


//    @Query("select u from UserEntity u where (u.username = ?1 OR u.gmail = ?2) AND u.id <> ?3")
//    List<UserEntity> getByUsernameAndGmailIgnoreIdCase(String username, String gmail, int id);

    @Query("SELECT u FROM UserEntity u WHERE (u.username = :username OR u.gmail = :gmail) AND u.id <> :id")
    List<UserEntity> getByUsernameAndGmailIgnoreIdCase(@Param("username") String username,
                                                       @Param("gmail") String gmail,
                                                       @Param("id") int id);

}
