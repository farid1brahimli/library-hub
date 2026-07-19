package az.company.userservice.dao.repository;

import az.company.userservice.dao.entity.BorrowHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistoryEntity, Long> {
}
