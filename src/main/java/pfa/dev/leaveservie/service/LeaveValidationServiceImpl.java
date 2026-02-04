package pfa.dev.leaveservie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;
import pfa.dev.leaveservie.enities.LeaveStatus;
import pfa.dev.leaveservie.mapper.LeaveRequestMapper;
import pfa.dev.leaveservie.repositories.LeaveRequestRepository;

@Service
@RequiredArgsConstructor
public class LeaveValidationServiceImpl implements LeaveValidationService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;

    @Override
    public LeaveRequestDto approveLeaveRequest(Long requestId, Long managerId) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        // ✔ vérifier statut
        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending requests can be approved");
        }

        // ✔ ici tu peux vérifier manager responsable (si existant)
        // if (!leaveRequest.getManagerId().equals(managerId)) { ... }

        leaveRequest.setStatus(LeaveStatus.APPROVED);

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        return leaveRequestMapper.toDto(saved);
    }

    @Override
    public LeaveRequestDto rejectLeaveRequest(Long requestId, Long managerId, String reason) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (leaveRequest.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Only pending requests can be rejected");
        }

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setReason(reason);

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

        return leaveRequestMapper.toDto(saved);
    }
}
