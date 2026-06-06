package pfa.dev.leaveservie.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import pfa.dev.leaveservie.dto.LeaveTypeDto;
import pfa.dev.leaveservie.enities.LeaveType;

@Mapper(componentModel ="spring")

public interface LeaveTypeMapper {

    @Mappings(
            {

            }
    )
    LeaveTypeDto toDto(LeaveType leaveType);
    LeaveType toEntity(LeaveTypeDto leaveTypeDto);

}
