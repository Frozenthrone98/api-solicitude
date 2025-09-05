package co.pragma.powerup.api.config;

import co.pragma.powerup.api.dto.ErrorResponseDTO;
import co.pragma.powerup.api.exception.EmptyBodyException;
import co.pragma.powerup.config.ApiException;
import co.pragma.powerup.config.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static co.pragma.powerup.api.util.Constants.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.info("[INFO] HandlerErrors - start");
        return Mono.just(exchange.getResponse())
                .map(response -> {
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return response;
                }).flatMap(response -> {
                    if(ex instanceof ConstraintViolationException requestException) {
                        log.error("[ERROR] requestException - end");
                        return toListErrors(requestException.getConstraintViolations())
                                .flatMap(errors ->
                                        buildErrorResponse(exchange, HttpStatus.BAD_REQUEST,
                                                ErrorResponseDTO.builder()
                                                        .code(E001)
                                                        .message(REQUEST_VALIDATIONS_ERROR_E001)
                                                        .errorsDetails(errors)
                                                        .build()
                                ));
                    } else if(ex instanceof BusinessException businessException) {
                        log.error("[ERROR] businessException - end");
                        return buildErrorResponse(exchange, HttpStatus.BAD_REQUEST,
                                ErrorResponseDTO.builder()
                                        .code(businessException.getCode())
                                        .message(businessException.getMessage())
                                        .build()
                        );
                    } else if(ex instanceof EmptyBodyException emptyBodyException) {
                        log.error("[ERROR] emptyBodyException - end");
                        return buildErrorResponse(exchange, HttpStatus.BAD_REQUEST,
                                ErrorResponseDTO.builder()
                                        .code(emptyBodyException.getCode())
                                        .message(emptyBodyException.getMessage())
                                        .build()
                        );
                    } else if(ex instanceof ApiException apiException) {
                        log.error("[ERROR] apiException - end");
                        return buildErrorResponse(exchange, HttpStatus.BAD_REQUEST,
                                ErrorResponseDTO.builder()
                                        .code(apiException.getCode())
                                        .message(apiException.getMessage())
                                        .build()
                        );
                    } else if(ex instanceof WebClientRequestException webClientRequestException) {
                        log.error("[ERROR] webClientRequestException - end");
                        return buildErrorResponse(exchange, HttpStatus.BAD_REQUEST,
                                ErrorResponseDTO.builder()
                                        //.code(webClientRequestException.get)
                                        .message(webClientRequestException.getMessage())
                                        .build()
                        );
                    }

                    ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                            .code(E999)
                            .message(GENERIC_ERROR_E999)
                            .build();
                    log.info("[INFO] HandlerErrors - end");
                    return buildErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, errorResponseDTO);
                });
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, HttpStatus status, ErrorResponseDTO errorResponseDTO) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponseDTO);
            var buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error(GENERIC_ERROR_SERIALIZING, e);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }

    private Mono<Map<String,List<String>>> toListErrors(Set<ConstraintViolation<?>> violations) {
        return Flux.fromIterable(violations)
                .groupBy(violation -> violation.getPropertyPath().toString())
                .flatMap(groupedFlux ->
                        groupedFlux
                                .map(ConstraintViolation::getMessage)
                                .collectList()
                                .map(errorsByField -> Map.entry(groupedFlux.key(), errorsByField))
                )
                .collectMap(Map.Entry::getKey, Map.Entry::getValue);
    }
}
