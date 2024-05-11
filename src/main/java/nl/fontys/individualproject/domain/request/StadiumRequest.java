package nl.fontys.individualproject.domain.request;

import lombok.*;
import nl.fontys.individualproject.domain.enume.PlayingLocation;
import nl.fontys.individualproject.domain.enume.SurfaceType;

@Getter
@Builder
@AllArgsConstructor
public class StadiumRequest {
    private String name;
    private String address;
    private PlayingLocation playingLocation;
    private int capacity;
    private SurfaceType surfaceType;
}
