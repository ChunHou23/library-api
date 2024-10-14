package ab.interview.library_api.service;

import ab.interview.library_api.model.Book;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> findAllBooks();
    Book findBookById(Long id);
    List<Book> findBookByIsbnNumber(String isbnNumber);
    Long addBookValidator(String isbnNumber, String title, String author);
}
