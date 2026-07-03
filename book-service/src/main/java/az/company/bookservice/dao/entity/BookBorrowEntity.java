package az.company.bookservice.dao.entity;

import az.company.bookservice.model.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "borrows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookBorrowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long UserId;
    @Column(nullable = false)
    private LocalDateTime borrowedAt;
    @Column(nullable = false)
    private LocalDate dueTime;
    private LocalDateTime returnedAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookBorrowEntity that = (BookBorrowEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
