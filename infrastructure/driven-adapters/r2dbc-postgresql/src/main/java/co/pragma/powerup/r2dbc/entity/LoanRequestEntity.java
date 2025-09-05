package co.pragma.powerup.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("loan_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestEntity {
    @Id
    private Long id;
    private BigDecimal amount;
    private Integer term;
    private String email;
    @Column(value = "id_statement")
    private Long idStatement;
    @Column(value = "id_loan_type")
    private Long idLoanType;
}
