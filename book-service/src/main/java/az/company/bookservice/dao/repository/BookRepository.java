package az.company.bookservice.dao.repository;

import az.company.bookservice.dao.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
