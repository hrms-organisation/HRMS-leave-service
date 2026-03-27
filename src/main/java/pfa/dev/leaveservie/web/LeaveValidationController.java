package pfa.dev.leaveservie.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.dto.RejectLeaveRequestDto;
import pfa.dev.leaveservie.service.LeaveValidationService;

@RestController
@RequestMapping("/leave-validation")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR') ")

public class LeaveValidationController {

    private final LeaveValidationService leaveValidationService;

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequestDto> approveLeave(
            @PathVariable Long id,
            @RequestParam Long managerId) {

        return ResponseEntity.ok(
                leaveValidationService.approveLeaveRequest(id, managerId)
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequestDto> rejectLeave(
            @PathVariable Long id,
            @RequestParam Long managerId,
            @RequestBody RejectLeaveRequestDto dto) {

        return ResponseEntity.ok(
                leaveValidationService.rejectLeaveRequest(
                        id,
                        managerId,
                        dto.getReason()
                )
        );
    }
}
