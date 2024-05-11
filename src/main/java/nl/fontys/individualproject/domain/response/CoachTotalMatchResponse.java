package nl.fontys.individualproject.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoachTotalMatchResponse {
    private int totalMatches;
}
