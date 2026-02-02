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

    private LeaveType leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer totalDays;

    private LeaveStatus status;


    private String reason;



}
