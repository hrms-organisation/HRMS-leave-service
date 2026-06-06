package pfa.dev.leaveservie.dto;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveTypeDto {


    private Long id;

    private String name;

    private String paid;

    private String requiresApproval;

    private String requiresDocument;

    private Integer maxDaysPerYear;

    private boolean deductFromBalance;
}
