package az.company.bookservice.dao.repository;

import az.company.bookservice.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
