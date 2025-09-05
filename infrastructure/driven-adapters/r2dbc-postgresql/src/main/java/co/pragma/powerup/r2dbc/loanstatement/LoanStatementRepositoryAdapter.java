package co.pragma.powerup.r2dbc.loanstatement;

import co.pragma.powerup.model.loanstatement.LoanStatement;
import co.pragma.powerup.model.loanstatement.gateways.LoanStatementRepository;
import co.pragma.powerup.r2dbc.entity.LoanStatementEntity;
import co.pragma.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanStatementRepositoryAdapter extends ReactiveAdapterOperations<
        LoanStatement, LoanStatementEntity, Long, ILoanStatementRepository> implements LoanStatementRepository {
    public LoanStatementRepositoryAdapter(ILoanStatementRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanStatement.class));
    }
}
