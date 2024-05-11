package nl.fontys.individualproject.domain.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
}
