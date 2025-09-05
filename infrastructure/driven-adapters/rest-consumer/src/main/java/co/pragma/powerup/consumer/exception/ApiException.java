package co.pragma.powerup.consumer.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ApiException extends RuntimeException {
    private String code;
    private String message;
    @Builder.Default
    private Instant timestamp = Instant.now();
}
