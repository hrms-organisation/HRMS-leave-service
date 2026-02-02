package pfa.dev.leaveservie.mapper;

import org.mapstruct.Mapper;
import pfa.dev.leaveservie.dto.LeaveBalanceDto;
import pfa.dev.leaveservie.enities.LeaveBalance;

@Mapper(componentModel ="spring")
public interface LeaveBalanceMapper {
    LeaveBalanceDto toDto(LeaveBalance leaveBalance);
    LeaveBalance toEntity(LeaveBalanceDto leaveBalanceDto);

}
