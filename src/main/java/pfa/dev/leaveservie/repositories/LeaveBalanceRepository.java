package pfa.dev.leaveservie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pfa.dev.leaveservie.enities.LeaveBalance;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance,Long> {
}
