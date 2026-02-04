package pfa.dev.leaveservie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.boot.internal.Abstract;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class UpdateLeaveRequestDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private Long leaveTypeId;

    private String reason;
}
