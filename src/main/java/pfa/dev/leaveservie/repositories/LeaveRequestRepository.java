package pfa.dev.leaveservie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pfa.dev.leaveservie.enities.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long> {
}
