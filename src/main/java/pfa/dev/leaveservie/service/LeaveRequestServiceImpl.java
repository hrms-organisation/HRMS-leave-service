package pfa.dev.leaveservie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.dto.UpdateLeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;
import pfa.dev.leaveservie.enities.LeaveStatus;
import pfa.dev.leaveservie.enities.LeaveType;
import pfa.dev.leaveservie.mapper.LeaveRequestMapper;
import pfa.dev.leaveservie.repositories.LeaveRequestRepository;
import pfa.dev.leaveservie.repositories.LeaveTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;
    private final LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveRequestDto submitLeaveRequest(LeaveRequestDto leaveRequest) {
        List<LeaveRequest> existingLeaves =
                leaveRequestRepository.findOverlappingLeave(
                        leaveRequest.getEmployeeId(),
                        leaveRequest.getStartDate(),
                        leaveRequest.getEndDate()
                );

        if (!existingLeaves.isEmpty()) {
            throw new RuntimeException("Employee already has a leave request during this period");
        }
        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequestMapper.toEntity(leaveRequest));

        return leaveRequestMapper.toDto(savedLeaveRequest);
    }

    @Override
    public LeaveRequestDto updateLeaveRequest(Long id, UpdateLeaveRequestDto dto) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
                .orElseThrow(() -> new RuntimeException("Leave type not found"));

        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setReason(dto.getReason());

        return leaveRequestMapper.toDto(
                leaveRequestRepository.save(leaveRequest)
        );
    }

    @Override
    public void cancelLeaveRequest(Long requestId, Long employeeId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (!leaveRequest.getEmployeeId().equals(employeeId)) {
            throw new RuntimeException("You cannot cancel this leave request");
        }

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending requests can be cancelled");
        }

        leaveRequest.setStatus(LeaveStatus.CANCELLED);

        leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public Page<LeaveRequestDto> getPendingLeaveRequest(Pageable pageable) {
        return leaveRequestRepository
                .findByStatus(LeaveStatus.PENDING, pageable)
                .map(leaveRequestMapper::toDto);
    }

    @Override
    public Page<LeaveRequestDto> searchPendingLeaveRequest(String keyword, Pageable pageable) {
        return leaveRequestRepository
                .searchByStatus(LeaveStatus.PENDING, keyword, pageable)
                .map(leaveRequestMapper::toDto);
    }
}
