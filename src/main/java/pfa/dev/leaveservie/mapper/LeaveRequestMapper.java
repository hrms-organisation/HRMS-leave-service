package pfa.dev.leaveservie.mapper;

import org.mapstruct.Mapper;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;


@Mapper(componentModel ="spring")

public interface LeaveRequestMapper {
    LeaveRequestDto toDto(LeaveRequest leaveRequest);
    LeaveRequest toEntity(LeaveRequestDto leaveRequestDto);

}
