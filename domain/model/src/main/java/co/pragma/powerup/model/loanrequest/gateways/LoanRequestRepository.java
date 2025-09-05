package co.pragma.powerup.model.loanrequest.gateways;

import co.pragma.powerup.model.loanrequest.LoanRequest;
import reactor.core.publisher.Mono;

public interface LoanRequestRepository {
    Mono<Void> save(LoanRequest loanRequest);
}
