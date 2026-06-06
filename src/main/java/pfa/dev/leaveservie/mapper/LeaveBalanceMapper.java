package pfa.dev.leaveservie.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import pfa.dev.leaveservie.dto.LeaveBalanceDto;
import pfa.dev.leaveservie.enities.LeaveBalance;
import pfa.dev.leaveservie.enities.LeaveType;

@Mapper(componentModel ="spring")
public interface LeaveBalanceMapper {

    @Mappings({
            @Mapping(source = "leaveType.id", target = "idLeaveType")
    })
    LeaveBalanceDto toDto(LeaveBalance leaveBalance);

    @Mappings({
            @Mapping(source = "idLeaveType", target = "leaveType", qualifiedByName = "mapIdToLeaveType")
    })
    LeaveBalance toEntity(LeaveBalanceDto leaveBalanceDto);

    @Named("mapIdToLeaveType")
    default LeaveType mapIdToLeaveType(Long idLeaveType) {
        if (idLeaveType == null) {
            return null;
        }
        LeaveType leaveType = new LeaveType();
        leaveType.setId(idLeaveType);
        return leaveType;
    }
}
