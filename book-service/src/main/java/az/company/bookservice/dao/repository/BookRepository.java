package az.company.bookservice.dao.repository;

import az.company.bookservice.dao.entity.BookEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query
            ("""
SELECT b FROM BookEntity b
WHERE (:categoryId IS NULL OR b.category.id = :categoryId)
AND (:author IS NULL OR b.author = :author)
"""

    )
    Page<BookEntity> findAll(Pageable pageable, Long categoryId, String author);


    @Query
            ("""
SELECT b FROM BookEntity b
WHERE (:author IS NULL OR b.author = :author)
AND (:title IS NULL OR b.title = :title)
"""
    )
    List<BookEntity> findAllBooksWithAuthorAndTitle(String author, String title);

}
