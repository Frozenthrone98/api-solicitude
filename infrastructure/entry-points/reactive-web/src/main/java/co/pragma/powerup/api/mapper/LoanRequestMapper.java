package co.pragma.powerup.api.mapper;

import co.pragma.powerup.api.dto.LoanRequestDTO;
import co.pragma.powerup.model.loanrequest.LoanRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {
    LoanRequest toModel(LoanRequestDTO loanRequestDTO);
}
