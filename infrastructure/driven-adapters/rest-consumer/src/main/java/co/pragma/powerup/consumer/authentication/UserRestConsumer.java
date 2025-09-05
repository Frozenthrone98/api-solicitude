package co.pragma.powerup.consumer.authentication;

import co.pragma.powerup.consumer.exception.ApiException;
import co.pragma.powerup.consumer.mapper.UserMapper;
import co.pragma.powerup.rest.user.User;
import co.pragma.powerup.rest.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static co.pragma.powerup.consumer.util.Constants.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserRestConsumer implements UserRepository {
    private final WebClient client;
    //private final UserMapper mapper;

    @Override
    public Mono<Boolean> validate(String identityDocument) {
        return validateUser(identityDocument);
    }

    @CircuitBreaker(name = "validateUser", fallbackMethod = "validateUserFallback")
    public Mono<Boolean> validateUser(String identityDocument) {
        log.info("[INFO] rest validateUser - start");
        return client.get()
                .uri("/api/v1/users/validate/{identityDocument}", identityDocument)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToMono(UserResponse.class)
                .map(userResponse -> userResponse.exists())
                .doOnSuccess(response -> log.info("[INFO] - rest validateUser completed successfully"))
                .doOnError(error -> log.error("[ERROR] - rest validateUser failure", error));
    }

    private Mono<Throwable> handleServerError(ClientResponse response) {
        log.error("[ERROR] - User service 5xx error");
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(ApiException.builder()
                        .code(CX001)
                        .message(API_USER_EXTERNAL_SERVICE_ERROR)
                        .build())
                );
    }

    private Mono<Throwable> handleClientError(ClientResponse response) {
        log.warn("[WARN] - User service 4xx error");
        return Mono.error(ApiException.builder()
                .code(CX001)
                .message(API_USER_EXTERNAL_BAD_REQUEST)
                .build()
        );
    }

    public Mono<Boolean> validateUserFallback(String identityDocument, Exception ex) {
        log.info("[INFO] - Flow fallback active");
        return Mono.just(true);
    }

}
