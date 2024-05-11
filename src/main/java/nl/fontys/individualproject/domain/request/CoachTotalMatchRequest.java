package nl.fontys.individualproject.domain.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CoachTotalMatchRequest {
    private LocalDate date_start;
    private LocalDate date_end;
}
