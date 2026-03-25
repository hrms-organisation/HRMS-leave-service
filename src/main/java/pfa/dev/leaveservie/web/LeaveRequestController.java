package pfa.dev.leaveservie.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.dto.UpdateLeaveRequestDto;
import pfa.dev.leaveservie.service.LeaveRequestService;

@RestController
@RequestMapping("/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveRequestDto> submitLeaveRequest(
            @RequestBody @Valid LeaveRequestDto dto) {

        return ResponseEntity.ok(
                leaveRequestService.submitLeaveRequest(dto)
        );
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveRequestDto> updateLeaveRequest(
            @PathVariable Long id,
            @RequestBody @Valid UpdateLeaveRequestDto dto) {

        return ResponseEntity.ok(
                leaveRequestService.updateLeaveRequest(id, dto)
        );
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> cancelLeaveRequest(
            @PathVariable Long id,
            @RequestParam Long employeeId) {

        leaveRequestService.cancelLeaveRequest(id, employeeId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<Page<LeaveRequestDto>> getPendingLeaveRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                leaveRequestService.getPendingLeaveRequest(PageRequest.of(page, size))
        );
    }

    @GetMapping("/pending/search")
    @PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
    public ResponseEntity<Page<LeaveRequestDto>> searchPendingLeaveRequest(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                leaveRequestService.searchPendingLeaveRequest(keyword, PageRequest.of(page, size))
        );
    }
}
