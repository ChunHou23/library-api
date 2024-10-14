package ab.interview.library_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "borrow_record", schema = "public")
public class BorrowRecord {
    public static final Integer ACTIVE = 1;
    public static final Integer INACTIVE = 2;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "borrower_id")
    private Long borrowerId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
