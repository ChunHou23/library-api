package ab.interview.library_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "book", schema = "public")
public class Book {
    public static final Integer ACTIVE = 1;
    public static final Integer OCCUPIED = 2;
    public static final Integer DELETED = 3;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn_number")
    private String isbnNumber;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
