package pfa.dev.leaveservie.enities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "leave_type")
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String paid;

    @Column(nullable = false)
    private String requiresApproval;

    @Column(nullable = false)
    private String requiresDocument;

    @Column
    private Integer maxDaysPerYear;

    @Column(nullable = false)
    private boolean deductFromBalance;
}

