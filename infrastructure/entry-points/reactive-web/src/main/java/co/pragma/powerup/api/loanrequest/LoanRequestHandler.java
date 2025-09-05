package co.pragma.powerup.api.loanrequest;

import co.pragma.powerup.api.dto.LoanRequestDTO;
import co.pragma.powerup.api.exception.EmptyBodyException;
import co.pragma.powerup.api.mapper.LoanRequestMapper;
import co.pragma.powerup.api.util.ValidatorUtil;
import co.pragma.powerup.usecase.loanrequest.LoanRequestUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.pragma.powerup.api.util.Constants.E002;
import static co.pragma.powerup.api.util.Constants.EMPTY_BODY_ERROR_E002;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoanRequestHandler {
    private final LoanRequestUseCase loanRequestUseCase;
    private final LoanRequestMapper loanRequestMapper;
    private final ValidatorUtil validatorUtil;

    public Mono<ServerResponse> register(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanRequestDTO.class)
                .switchIfEmpty(Mono.error(EmptyBodyException.builder()
                        .code(E002)
                        .message(EMPTY_BODY_ERROR_E002)
                        .build()))
                .flatMap(validatorUtil::validate)
                .map(loanRequestMapper::toModel)
                .flatMap(loanRequestUseCase::register)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .doOnSuccess(response -> log.info("[INFO] - HTTP response built successfully"))
                .doOnError(error -> log.error("[ERROR] - Error building response: {}", error.getMessage()));
    }
}
