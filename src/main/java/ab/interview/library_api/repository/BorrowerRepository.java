package ab.interview.library_api.repository;

import ab.interview.library_api.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    @Query(value = "SELECT b " +
            "FROM Borrower b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Borrower> searchByName(@Param("name") String name);
}
