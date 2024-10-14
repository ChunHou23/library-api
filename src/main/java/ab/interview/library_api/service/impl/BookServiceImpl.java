package ab.interview.library_api.service.impl;

import ab.interview.library_api.model.Book;
import ab.interview.library_api.repository.BookRepository;
import ab.interview.library_api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findBookByIsbnNumber(String isbnNumber) {
        return bookRepository.findByIsbnNumber(isbnNumber);
    }

    public Long addBookValidator(String isbnNumber, String title, String author) {
        return bookRepository.checkIfValidBook(isbnNumber, title, author);
    }
}
