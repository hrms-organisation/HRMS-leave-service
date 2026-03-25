package pfa.dev.leaveservie.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfa.dev.leaveservie.dto.LeaveRequestDto;

import pfa.dev.leaveservie.service.LeaveHistoryService;


@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class LeaveHistoryConroller {
    private final LeaveHistoryService leaveHistoryService;



    @GetMapping("get-by-employee/{idEmployee}")

    public ResponseEntity<Page<LeaveRequestDto>> getLeaveHistoryByEmployee( @RequestParam(defaultValue = "0") int page  , @RequestParam(defaultValue = "10") int size , @PathVariable Long idEmployee) {
        return ResponseEntity.ok(leaveHistoryService.getLeaveHistoryByEmployee(idEmployee , PageRequest.of(page, size)));

    }

    @GetMapping("/get-by-status")
    public ResponseEntity<Page<LeaveRequestDto>> getLeaveHistoryByStatus(@RequestParam Long employeeId, @RequestParam Long leaveTypeId,
                                                                         @RequestParam(defaultValue = "0") int page  , @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(leaveHistoryService.getLeaveHistoryByType(employeeId, leaveTypeId, PageRequest.of(page, size)));
    }

    @GetMapping("/get-total")
    public ResponseEntity<Integer> getTakenLeaveHistory(@RequestParam Long employeeId, @RequestParam Long leaveTypeId) {
        return ResponseEntity.ok(
                leaveHistoryService.getTotalLeaveTaken(employeeId,leaveTypeId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<LeaveRequestDto>> searchLeaveHistory(
            @RequestParam Long employeeId,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                leaveHistoryService.searchLeaveHistory(employeeId, keyword, PageRequest.of(page, size))
        );
    }


}
