package nl.fontys.individualproject.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class Match {
    private int id;
    private Stadium stadium;
    private LocalDate date;
    private LocalTime time;
    private LocalTime time_end;
    private Coach coach;
    private List<Player> players;
}
