package pfa.dev.leaveservie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.dto.UpdateLeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;
import pfa.dev.leaveservie.enities.LeaveStatus;
import pfa.dev.leaveservie.mapper.LeaveRequestMapper;
import pfa.dev.leaveservie.repositories.LeaveRequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;

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
        LeaveRequest leaveRequest1 = leaveRequestRepository.save(leaveRequestMapper.toEntity(leaveRequest));

        return leaveRequestMapper.toDto(leaveRequest1);
    }

    @Override
    public LeaveRequestDto updateLeaveRequest(Long id, UpdateLeaveRequestDto dto) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setId(dto.getLeaveTypeId());
        leaveRequest.setReason(dto.getReason());

        return leaveRequestMapper.toDto(
                leaveRequestRepository.save(leaveRequest)
        );
    }


    @Override
    public void cancelLeaveRequest(Long requestId, Long employeeId) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        // Vérifier que la demande appartient à l'employé
        if (!leaveRequest.getEmployeeId().equals(employeeId)) {
            throw new RuntimeException("You cannot cancel this leave request");
        }

        // Vérifier statut
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

}
