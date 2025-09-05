package co.pragma.powerup.usecase.loanrequest;

import co.pragma.powerup.config.BusinessException;
import co.pragma.powerup.model.loanrequest.LoanRequest;
import co.pragma.powerup.model.loanrequest.gateways.LoanRequestRepository;
import co.pragma.powerup.model.loantype.LoanType;
import co.pragma.powerup.model.loantype.gateways.LoanTypeRepository;
import co.pragma.powerup.rest.user.User;
import co.pragma.powerup.rest.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static co.pragma.powerup.usecase.util.Constants.*;

@RequiredArgsConstructor
public class LoanRequestUseCase {
    private static final Logger log = Logger.getLogger(LoanRequestUseCase.class.getName());
    private final LoanRequestRepository loanRequestRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final UserRepository userRepository;

    public Mono<Void> register(LoanRequest loanRequest) {
        return validateUserExists(loanRequest)
                .then(findAndValidateLoanType(loanRequest))
                .map(this::buildLoanRequestDefault)
                .flatMap(loanRequestRepository::save)
                .doOnSuccess(unused -> log.info("[INFO] - LoanRequest saved successfully"))
                .doOnError(error -> {
                    log.severe("[ERROR] - Error saving loanRequest: " + error.getMessage());
                    error.printStackTrace();
                }).then();
    }

    private LoanRequest buildLoanRequestDefault(LoanRequest validatedLoanRequest) {
        return LoanRequest.builder()
                .email(validatedLoanRequest.getEmail())
                .term(validatedLoanRequest.getTerm())
                .identityDocument(validatedLoanRequest.getIdentityDocument())
                .amount(validatedLoanRequest.getAmount())
                .idLoanType(validatedLoanRequest.getIdLoanType())
                .idStatement(ID_LOAN_STATEMENT_PENDING)
                .build();
    }

    private Mono<Void> validateUserExists(LoanRequest loanRequest) {
        return userRepository.validate(loanRequest.getIdentityDocument())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B004)
                        .message(BUSINESS_VALIDATIONS_EXISTS_USER_B004)
                        .build())
                ).then();
    }

    private Mono<LoanRequest> findAndValidateLoanType(LoanRequest loanRequest) {
        return loanTypeRepository.findById(loanRequest.getIdLoanType())
                .switchIfEmpty(Mono.error(BusinessException.builder()
                        .code(B001)
                        .message(BUSINESS_VALIDATIONS_LOAN_TYPE_NOT_FOUND_B001)
                        .build()))
                .flatMap(loanType -> validateLoanTypeConditions(loanType, loanRequest));
    }

    private Mono<LoanRequest> validateLoanTypeConditions(LoanType loanType, LoanRequest loanRequest) {
        return Mono.just(loanRequest)
                .filterWhen(loanReq -> validateAmountNotNull(loanReq))
                .filterWhen(loanReq -> validateMinAmount(loanReq, loanType))
                .filterWhen(loanReq -> validateMaxAmount(loanReq, loanType))
                .thenReturn(loanRequest);
    }

    private Mono<Boolean> validateAmountNotNull(LoanRequest loanRequest) {
        return Mono.just(loanRequest)
                .map(loanReq -> loanReq.getAmount() != null)
                .flatMap(isValid -> isValid ?
                        Mono.just(true) :
                        Mono.error(BusinessException.builder()
                                .code(B002)
                                .message(BUSINESS_VALIDATIONS_AMOUNT_REQUEST_B002)
                                .build()));
    }

    private Mono<Boolean> validateMinAmount(LoanRequest loanRequest, LoanType loanType) {
        return Mono.just(loanRequest)
                .map(loanReq -> loanReq.getAmount().compareTo(loanType.getAmountMin()) >= 0)
                .flatMap(isValid -> isValid ?
                        Mono.just(true) :
                        Mono.error(BusinessException.builder()
                                .code(B003)
                                .message(BUSINESS_VALIDATIONS_MIN_ALLOW_BY_LOAN_B003 + loanType.getAmountMin().toString())
                                .build()));
    }

    private Mono<Boolean> validateMaxAmount(LoanRequest loanRequest, LoanType loanType) {
        return Mono.just(loanRequest)
                .map(loanReq -> loanReq.getAmount().compareTo(loanType.getAmountMax()) <= 0)
                .flatMap(isValid -> isValid ?
                        Mono.just(true) :
                        Mono.error(BusinessException.builder()
                                .code(B003)
                                .message(BUSINESS_VALIDATIONS_MAX_ALLOW_BY_LOAN_B003 + loanType.getAmountMax().toString())
                                .build()));
    }
}
