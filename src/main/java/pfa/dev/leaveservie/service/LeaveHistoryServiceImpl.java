package pfa.dev.leaveservie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;
import pfa.dev.leaveservie.enities.LeaveStatus;
import pfa.dev.leaveservie.enities.LeaveType;
import pfa.dev.leaveservie.exception.ResourceNotFoundException;
import pfa.dev.leaveservie.mapper.LeaveRequestMapper;
import pfa.dev.leaveservie.mapper.LeaveTypeMapper;
import pfa.dev.leaveservie.repositories.LeaveRequestRepository;
import pfa.dev.leaveservie.repositories.LeaveTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor



public class LeaveHistoryServiceImpl implements LeaveHistoryService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;
    private final LeaveTypeRepository  leaveTypeRepository;



    @Override

    public Page<LeaveRequestDto> getLeaveHistoryByEmployee(Long employeeId, Pageable pageable) {

        validateEmployee(employeeId);
        Page<LeaveRequest> leaveRequest= leaveRequestRepository.findByEmployeeId(employeeId, pageable);



        return leaveRequest.map(leaveRequestMapper::toDto) ;
    }

    @Override
    public Page<LeaveRequestDto> getLeaveHistoryByType(Long employeeId, Long leaveTypeId, Pageable pageable) {
    validateEmployee(employeeId);
    validateLeaveType(leaveTypeId);
        Page<LeaveRequest> leaveRequest = leaveRequestRepository.findByEmployeeIdAndLeaveType_Id(employeeId, leaveTypeId, pageable);

        return leaveRequest.map(leaveRequestMapper::toDto) ;
    }

    @Override
    public Integer getTotalLeaveTaken(Long employeeId, Long leaveTypeId) {
        validateEmployee(employeeId);
        validateLeaveType(leaveTypeId);
        List<LeaveRequest> leaves =
                leaveRequestRepository
                        .findByEmployeeIdAndLeaveType_IdAndStatus(
                                employeeId,
                                leaveTypeId,
                                LeaveStatus.APPROVED
                        );

        return leaves.stream()
                .mapToInt(LeaveRequest::getTotalDays)
                .sum();
    }

    @Override
    public Page<LeaveRequestDto> searchLeaveHistory(Long employeeId, String keyword, Pageable pageable) {
        validateEmployee(employeeId);

        return leaveRequestRepository
                .searchEmployeeHistory(employeeId, keyword, pageable)
                .map(leaveRequestMapper::toDto);
    }


    private void validateEmployee(Long employeeId) {
        if (!leaveRequestRepository.existsByEmployeeId(employeeId)) {
            throw new ResourceNotFoundException("No leave history for this employee");
        }
    }

    private void validateLeaveType(Long leaveTypeId) {
        if (!leaveTypeRepository.existsById(leaveTypeId)) {
            throw new ResourceNotFoundException("Leave type not found");
        }
    }


}
