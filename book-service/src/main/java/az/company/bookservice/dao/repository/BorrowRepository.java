package az.company.bookservice.dao.repository;

import aj.org.objectweb.asm.commons.Remapper;
import az.company.bookservice.dao.entity.BookBorrowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRepository extends JpaRepository<BookBorrowEntity, Long> {

    Optional<BookBorrowEntity> findByBookIdAndUserId(Long bookId, Long userId);
    Page<BookBorrowEntity> findByUserId(Long userId, Pageable pageable);
}
