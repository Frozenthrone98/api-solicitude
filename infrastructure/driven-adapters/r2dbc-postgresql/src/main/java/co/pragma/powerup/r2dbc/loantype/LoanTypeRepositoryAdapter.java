package co.pragma.powerup.r2dbc.loantype;

import co.pragma.powerup.model.loantype.LoanType;
import co.pragma.powerup.model.loantype.gateways.LoanTypeRepository;
import co.pragma.powerup.r2dbc.entity.LoanTypeEntity;
import co.pragma.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanTypeRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType, LoanTypeEntity, Long, ILoanTypeRepository> implements LoanTypeRepository {
    public LoanTypeRepositoryAdapter(ILoanTypeRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class));
    }

}
