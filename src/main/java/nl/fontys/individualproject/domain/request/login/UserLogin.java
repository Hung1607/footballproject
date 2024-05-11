package nl.fontys.individualproject.domain.request.login;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserLogin {
    private String username;
    private String password;
}
