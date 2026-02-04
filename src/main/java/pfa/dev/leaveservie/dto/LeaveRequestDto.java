package pfa.dev.leaveservie.dto;

import lombok.*;
import pfa.dev.leaveservie.enities.LeaveStatus;
import pfa.dev.leaveservie.enities.LeaveType;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDto {

    private Long id;

    private Long employeeId;

    private Long idLeaveType;


    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalDays;

    private String status;


    private String reason;



}
