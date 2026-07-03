package az.company.bookservice.dao.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "category")
    private List<BookEntity> books;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
