package co.pragma.powerup.consumer.authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRequest {
    private String identityDocument;
    private String email;
}
