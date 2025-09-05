package co.pragma.powerup.model.loanrequest;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanRequest {
    private Long id;
    private BigDecimal amount;
    private Integer term;
    private String email;
    private String identityDocument;
    private Long idStatement;
    private Long idLoanType;
}
