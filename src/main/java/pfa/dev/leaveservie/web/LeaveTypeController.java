package pfa.dev.leaveservie.web;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pfa.dev.leaveservie.dto.LeaveTypeDto;
import pfa.dev.leaveservie.enities.LeaveType;
import pfa.dev.leaveservie.repositories.LeaveTypeRepository;
import pfa.dev.leaveservie.service.LeaveTypeService;

@RestController
@RequestMapping("/leave")
@RequiredArgsConstructor
@PreAuthorize("hasRole('HR')")
public class LeaveTypeController {
    private final LeaveTypeService leaveTypeService;


    @PostMapping("/add")
    public ResponseEntity<LeaveTypeDto> createLeaveType(
           @RequestBody LeaveTypeDto leaveType) {
        return ResponseEntity.ok(leaveTypeService.createLeaveType(leaveType));

    }
    @GetMapping("/getall")
    public ResponseEntity<Page<LeaveTypeDto>> findAllLeaveType(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(leaveTypeService.findLeaveTypeByPage(PageRequest.of(page, size, Sort.by("id").descending())));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveTypeDto> getLeaveTypeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(leaveTypeService.findLeaveTypeById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<LeaveTypeDto> updateLeaveType(
            @RequestBody LeaveTypeDto leaveType , @PathVariable Long id
    ){

        return ResponseEntity.ok(leaveTypeService.updateLeaveType(leaveType, id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLeaveType(
            @PathVariable Long id
    ){
         leaveTypeService.deleteLeaveTypeById(id);
    }



    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
