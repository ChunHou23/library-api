package ab.interview.library_api.repository;

import ab.interview.library_api.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE BorrowRecord a SET a.status = :status, a.updatedAt = :updatedAt WHERE a.id = :id")
    void updateRecordToReturn(@Param("id") Long id, Integer status, @Param("updatedAt") LocalDate updatedAt);

    @Query(value = "SELECT count(a.id) = 0 FROM BorrowRecord a WHERE a.status = :status AND a.resourceId = :resourceId")
    Boolean validateResourceAvailability(@Param("resourceId") Long resourceId, Integer status);
}
