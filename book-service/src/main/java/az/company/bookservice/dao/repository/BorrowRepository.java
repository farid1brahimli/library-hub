package az.company.bookservice.dao.repository;

import az.company.bookservice.dao.entity.BookBorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<BookBorrowEntity, Long> {
}
