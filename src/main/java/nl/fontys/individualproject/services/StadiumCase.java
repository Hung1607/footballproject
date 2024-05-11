package nl.fontys.individualproject.services;

import nl.fontys.individualproject.domain.response.Stadium;
import nl.fontys.individualproject.domain.request.StadiumRequest;

import java.util.List;
import java.util.Optional;


public interface StadiumCase {
    void createStadium(StadiumRequest stadiumRequest);
    List<Stadium> getAllStadium();
    Optional<Stadium> getStadiumByID(int id);
    void updateStadium(Stadium updatedStadium);
    void deleteStadiumByID(int id);
}
