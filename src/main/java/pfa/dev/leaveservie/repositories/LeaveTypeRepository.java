package pfa.dev.leaveservie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pfa.dev.leaveservie.enities.LeaveType;

public interface LeaveTypeRepository extends JpaRepository<LeaveType,Long> {
    LeaveType findByName(String name);
}
