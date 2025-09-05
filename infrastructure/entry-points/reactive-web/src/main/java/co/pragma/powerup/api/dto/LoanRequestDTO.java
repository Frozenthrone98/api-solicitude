package co.pragma.powerup.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record LoanRequestDTO(

        @NotNull(message = "Amount must not be null")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Term must not be null")
        @Min(value = 1, message = "Term must be at least 1 month")
        @Max(value = 360, message = "Term cannot exceed 360 months")
        Integer term,

        @NotBlank(message = "Identity document must not be blank")
        @Pattern(regexp = "\\d{8,12}", message = "Identity document must be 8-12 digits")
        String identityDocument,

        @NotBlank(message = "Email must not be blank")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email must be valid")
        String email,

        @NotNull(message = "Loan Type ID must not be null")
        @Positive(message = "Loan Type ID must be positive")
        Long idLoanType
) {
}
