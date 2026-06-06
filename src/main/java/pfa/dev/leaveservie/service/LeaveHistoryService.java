package pfa.dev.leaveservie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pfa.dev.leaveservie.dto.LeaveRequestDto;

public interface LeaveHistoryService {

    Page<LeaveRequestDto> getLeaveHistoryByEmployee(Long employeeId, Pageable pageable);

    Page<LeaveRequestDto> getLeaveHistoryByType(Long employeeId, Long leaveTypeId, Pageable pageable);

    Integer getTotalLeaveTaken(Long employeeId, Long leaveTypeId);

    Page<LeaveRequestDto> searchLeaveHistory(Long employeeId, String keyword, Pageable pageable);

}
