package ab.interview.library_api.application;

import ab.interview.library_api.model.Book;
import ab.interview.library_api.model.BookType;
import ab.interview.library_api.model.dto.in.AddBookInDTO;
import ab.interview.library_api.model.dto.out.BookOutDTO;
import ab.interview.library_api.model.mapper.BookMapper;
import ab.interview.library_api.model.mapper.BorrowRecordMapper;
import ab.interview.library_api.model.mapper.BorrowerMapper;
import ab.interview.library_api.service.BookService;
import ab.interview.library_api.service.BookTypeService;
import ab.interview.library_api.service.BorrowRecordService;
import ab.interview.library_api.service.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookBusiness {
//    @Autowired
//    private BookTypeService bookTypeService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    public BookOutDTO addBook(AddBookInDTO addBookInDTO) {
        try {
            if (!checkValidBook(addBookInDTO)) {
                log.warn("[Invalid Author / Title / ISBN Number]: Unable to register new book");
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Invalid Book Author / Title / ISBN Number");
            }

//            BookType bookType = bookTypeService.findBookById(addBookInDTO.getBookType());
//            if (Objects.isNull(bookType)) {
//                log.warn("[Invalid Book Type]: Unable to register new book");
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Book Type");
//            }

            Book createdBook = bookService.addBook(bookMapper.createNewBook(addBookInDTO));
            return bookMapper.convertToDTO(createdBook);
        } catch (Exception e) {
            log.warn("addBook: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register book");
        }
    }

    private boolean checkValidBook(AddBookInDTO addBookInDTO) {
        Long bookWithSameIsbnNumber = bookService.addBookValidator(
                addBookInDTO.getIsbnNumber(),
                addBookInDTO.getTitle(),
                addBookInDTO.getAuthor()
        );

        return bookWithSameIsbnNumber <= 0;
    }

    public List<BookOutDTO> getAllBooks() {
        try {
            List<Book> bookList = bookService.findAllBooks();
            if (CollectionUtils.isEmpty(bookList)) {
                log.info("[bookList is empty]: No book found");
                return new ArrayList<>();
            }

            return bookMapper.convertToDTOList(bookList);
        } catch (Exception e) {
            log.warn("getAllBooks: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get all books");
        }
    }
}
