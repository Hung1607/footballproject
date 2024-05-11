package nl.fontys.individualproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nl.fontys.individualproject.domain.response.Coach;
import nl.fontys.individualproject.domain.response.Stadium;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class MatchRequest {
    private Stadium stadium;
    private LocalDate date;
    private LocalTime time;
    private LocalTime time_end;
    private Coach coach;
}
