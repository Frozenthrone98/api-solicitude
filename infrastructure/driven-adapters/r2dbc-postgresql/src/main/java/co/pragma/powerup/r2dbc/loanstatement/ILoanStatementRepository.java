package co.pragma.powerup.r2dbc.loanstatement;

import co.pragma.powerup.r2dbc.entity.LoanStatementEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ILoanStatementRepository extends ReactiveCrudRepository<LoanStatementEntity, Long>, ReactiveQueryByExampleExecutor<LoanStatementEntity> {
}
