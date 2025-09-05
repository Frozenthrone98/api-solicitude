package co.pragma.powerup.r2dbc.loanrequest;

import co.pragma.powerup.r2dbc.entity.LoanRequestEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ILoanRequestRepository extends ReactiveCrudRepository<LoanRequestEntity, Long>, ReactiveQueryByExampleExecutor<LoanRequestEntity> {
}