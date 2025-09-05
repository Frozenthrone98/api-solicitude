package co.pragma.powerup.r2dbc.loantype;

import co.pragma.powerup.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ILoanTypeRepository extends ReactiveCrudRepository<LoanTypeEntity, Long>, ReactiveQueryByExampleExecutor<LoanTypeEntity> {
}
