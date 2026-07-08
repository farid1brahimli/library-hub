package az.company.userservice.dao.repository;

import az.company.userservice.dao.entity.BorrowsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowHistoryRepository extends JpaRepository<BorrowsHistoryEntity, Long> {
}
