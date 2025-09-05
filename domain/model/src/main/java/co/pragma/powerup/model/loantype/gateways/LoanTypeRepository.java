package co.pragma.powerup.model.loantype.gateways;

import co.pragma.powerup.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanType> findById(Long id);
}
