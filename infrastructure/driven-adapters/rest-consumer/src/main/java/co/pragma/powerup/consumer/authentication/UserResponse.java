package co.pragma.powerup.consumer.authentication;

public record UserResponse(
        Boolean exists,
        String message
) {
}
