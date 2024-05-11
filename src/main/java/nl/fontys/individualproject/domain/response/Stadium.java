package nl.fontys.individualproject.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.SurfaceType;

@Builder
@Getter
@Setter
public class Stadium {
    private int id;
    private String name;
    private PlayingLocation playingLocation;
    private String address;
    private int capacity;
    private SurfaceType surfaceType;
}
