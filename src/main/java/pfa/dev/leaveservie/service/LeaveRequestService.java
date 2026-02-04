package pfa.dev.leaveservie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.dto.UpdateLeaveRequestDto;

public interface LeaveRequestService {
    LeaveRequestDto submitLeaveRequest(LeaveRequestDto leaveRequest); // Soumettre une nouvelle demande

    LeaveRequestDto updateLeaveRequest(Long id, UpdateLeaveRequestDto LeaveRequestDto); // Modifier une demande avant validation

    void cancelLeaveRequest(Long id,Long employeeId); // Annuler une demande de congé




    Page<LeaveRequestDto> getPendingLeaveRequest( Pageable pageable); // Lister toutes les demandes en attente
}
