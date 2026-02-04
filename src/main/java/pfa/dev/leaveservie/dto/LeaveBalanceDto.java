package pfa.dev.leaveservie.dto;

import lombok.*;
import pfa.dev.leaveservie.enities.LeaveType;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceDto {

    private Long id;

    private Long employeeId;

    private Long idLeaveType;

    private Integer remainingDays;


    private Integer usedDays;
}
