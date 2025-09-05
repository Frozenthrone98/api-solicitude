package co.pragma.powerup.api.loanrequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoanRequestRouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(LoanRequestHandler loanRequestHandler) {
        return route(POST("/api/v1/loan-requests"), loanRequestHandler::register);
    }
}
