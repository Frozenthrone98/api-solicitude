package co.pragma.powerup.config;

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
