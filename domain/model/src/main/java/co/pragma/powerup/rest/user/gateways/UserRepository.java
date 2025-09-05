package co.pragma.powerup.rest.user.gateways;

import co.pragma.powerup.rest.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Boolean> validate(String identityDocument);
}
