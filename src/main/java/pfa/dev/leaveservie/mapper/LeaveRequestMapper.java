package pfa.dev.leaveservie.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;
import pfa.dev.leaveservie.enities.LeaveType;


@Mapper(componentModel ="spring")

public interface LeaveRequestMapper {
    @Mappings({
            @Mapping(source = "leaveType.id", target = "idLeaveType"),
    })
    LeaveRequestDto toDto(LeaveRequest leaveRequest);

    @Mappings({
            @Mapping(source = "idLeaveType", target = "leaveType", qualifiedByName = "mapIdToLeaveType")
    })
    LeaveRequest toEntity(LeaveRequestDto leaveRequestDto);

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
