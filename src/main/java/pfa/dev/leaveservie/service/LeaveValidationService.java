package pfa.dev.leaveservie.service;

import pfa.dev.leaveservie.dto.LeaveRequestDto;

public interface LeaveValidationService {

    LeaveRequestDto approveLeaveRequest(Long requestId, Long managerId);

    LeaveRequestDto rejectLeaveRequest(Long requestId, Long managerId, String reason);
}
