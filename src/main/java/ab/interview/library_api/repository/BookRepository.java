package ab.interview.library_api.repository;

import ab.interview.library_api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(
            value = "SELECT b.id, b.isbnNumber, b.title, b.author, b.status, b.createdAt, b.updatedAt " +
            "FROM Book b WHERE b.isbnNumber = :isbnNumber",
            nativeQuery = true
    )
    List<Book> findByIsbnNumber(@Param("isbnNumber") String isbnNumber);

    @Query(
            value = "SELECT COUNT(b.id) " +
                    "FROM Book b " +
                    "WHERE b.isbnNumber = :isbnNumber " +
                    "AND ( b.title not in (:title) OR b.author not in (:author))"
    )
    Long checkIfValidBook(@Param("isbnNumber") String isbnNumber, @Param("title") String title, @Param("author") String author);
}
