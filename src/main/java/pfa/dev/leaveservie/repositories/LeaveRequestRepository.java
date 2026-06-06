package pfa.dev.leaveservie.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pfa.dev.leaveservie.dto.LeaveRequestDto;
import pfa.dev.leaveservie.enities.LeaveRequest;
import pfa.dev.leaveservie.enities.LeaveStatus;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
    @Query("""
SELECT lr FROM LeaveRequest lr
WHERE lr.employeeId = :employeeId
AND lr.status <> 'REJECTED'
AND (
    (:startDate BETWEEN lr.startDate AND lr.endDate)
    OR
    (:endDate BETWEEN lr.startDate AND lr.endDate)
    OR
    (lr.startDate BETWEEN :startDate AND :endDate)
)
""")
    List<LeaveRequest> findOverlappingLeave(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );

    Page<LeaveRequest> findByStatus(LeaveStatus status, Pageable pageable);
    Page<LeaveRequest> findByEmployeeId(Long employeeId, Pageable pageable);
    boolean existsByEmployeeId(Long employeeId);
    boolean existsByIdAndEmployeeId(Long id, Long employeeId);

    Page<LeaveRequest> findByEmployeeIdAndLeaveType_Id(
            Long employeeId,
            Long leaveTypeId,
            Pageable pageable
    );

    List<LeaveRequest> findByEmployeeIdAndLeaveType_IdAndStatus(
            Long employeeId,
            Long leaveTypeId,
            LeaveStatus status
    );

    @Query("""
SELECT lr FROM LeaveRequest lr
WHERE lr.status = :status
AND (
    lower(coalesce(lr.reason, '')) LIKE lower(concat('%', :keyword, '%'))
    OR lower(cast(lr.status as string)) LIKE lower(concat('%', :keyword, '%'))
    OR cast(lr.id as string) LIKE concat('%', :keyword, '%')
    OR cast(lr.employeeId as string) LIKE concat('%', :keyword, '%')
    OR lower(cast(lr.startDate as string)) LIKE lower(concat('%', :keyword, '%'))
    OR lower(cast(lr.endDate as string)) LIKE lower(concat('%', :keyword, '%'))
)
""")
    Page<LeaveRequest> searchByStatus(
            LeaveStatus status,
            String keyword,
            Pageable pageable
    );

    @Query("""
SELECT lr FROM LeaveRequest lr
WHERE lr.employeeId = :employeeId
AND (
    lower(coalesce(lr.reason, '')) LIKE lower(concat('%', :keyword, '%'))
    OR lower(cast(lr.status as string)) LIKE lower(concat('%', :keyword, '%'))
    OR cast(lr.id as string) LIKE concat('%', :keyword, '%')
    OR lower(cast(lr.startDate as string)) LIKE lower(concat('%', :keyword, '%'))
    OR lower(cast(lr.endDate as string)) LIKE lower(concat('%', :keyword, '%'))
)
""")
    Page<LeaveRequest> searchEmployeeHistory(
            Long employeeId,
            String keyword,
            Pageable pageable
    );
}
