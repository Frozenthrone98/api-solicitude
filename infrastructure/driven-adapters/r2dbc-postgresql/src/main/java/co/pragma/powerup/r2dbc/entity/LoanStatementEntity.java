package co.pragma.powerup.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("loan_statements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatementEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
