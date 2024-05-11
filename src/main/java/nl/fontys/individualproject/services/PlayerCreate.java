package nl.fontys.individualproject.services;

import nl.fontys.individualproject.domain.request.PlayerRequest;

public interface PlayerCreate {
    void createPlayer(PlayerRequest playerRequest);
}
