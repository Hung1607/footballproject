package nl.fontys.individualproject.repositories;

import lombok.Generated;
import nl.fontys.individualproject.repositories.Entity.StadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Generated
public interface StadiumRepository extends JpaRepository<StadiumEntity, Integer> {
    boolean existsByName(String name);
    boolean existsByAddress(String address);

//    @Query("select s from StadiumEntity s where (s.name = ?1 OR s.address = ?2) AND s.id <> ?3")
//    List<StadiumEntity> getByNameAndAddressIgnoreIdCase(String name,String address,int id);

    @Modifying
    @Query("select s from StadiumEntity s where (s.name = :name or s.address = :address ) AND s.id <> :id") // Named parameters : name
    List<StadiumEntity> getByNameAndAddressIgnoreIdCase(@Param("name") String name,
                                                        @Param("address") String address,
                                                        @Param("id") int id);
}
