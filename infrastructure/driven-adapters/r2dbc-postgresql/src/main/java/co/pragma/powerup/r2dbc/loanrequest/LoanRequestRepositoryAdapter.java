package co.pragma.powerup.r2dbc.loanrequest;

import co.pragma.powerup.model.loanrequest.LoanRequest;
import co.pragma.powerup.model.loanrequest.gateways.LoanRequestRepository;
import co.pragma.powerup.r2dbc.entity.LoanRequestEntity;
import co.pragma.powerup.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class LoanRequestRepositoryAdapter extends ReactiveAdapterOperations<
        LoanRequest, LoanRequestEntity, Long, ILoanRequestRepository> implements LoanRequestRepository {

    public LoanRequestRepositoryAdapter(ILoanRequestRepository repository,
                                        ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanRequest.class));
    }

}