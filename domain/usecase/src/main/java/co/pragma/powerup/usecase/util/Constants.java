package co.pragma.powerup.usecase.util;

public class Constants {

    //VALUES
    public static final int ZERO = 0;
    public static final Long ID_LOAN_STATEMENT_PENDING = 1L;


    //ERRORS
    public static final String B001 = "B001";
    public static final String B002 = "B002";
    public static final String B003 = "B003";
    public static final String B004 = "B004";

    //MESSAGES
    public static final String BUSINESS_VALIDATIONS_LOAN_TYPE_NOT_FOUND_B001 = "Loan Type not found.";
    public static final String BUSINESS_VALIDATIONS_AMOUNT_REQUEST_B002 = "Amount request is required.";
    public static final String BUSINESS_VALIDATIONS_MIN_ALLOW_BY_LOAN_B003 = "Minimum allowed by loan is ";
    public static final String BUSINESS_VALIDATIONS_MAX_ALLOW_BY_LOAN_B003 = "Maximum allowed by loan is ";
    public static final String BUSINESS_VALIDATIONS_EXISTS_USER_B004 = "User does not exist in the system";

    private Constants() {
    }
}
