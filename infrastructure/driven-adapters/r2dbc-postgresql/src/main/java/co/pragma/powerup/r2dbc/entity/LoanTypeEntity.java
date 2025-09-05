package co.pragma.powerup.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("loan_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanTypeEntity {
    @Id
    private Long id;
    private String name;
    @Column(value = "amount_min")
    private BigDecimal amountMin;
    @Column(value = "amount_max")
    private BigDecimal amountMax;
    @Column(value = "interest_rate")
    private BigDecimal interestRate;
    @Column(value = "term_min")
    private Integer termMin;
    @Column(value = "valid_automatic")
    private Boolean validAutomatic;
}
