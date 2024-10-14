package ab.interview.library_api;

import ab.interview.library_api.application.BookBusiness;
import ab.interview.library_api.model.Book;
import ab.interview.library_api.model.dto.in.AddBookInDTO;
import ab.interview.library_api.model.dto.out.BookOutDTO;
import ab.interview.library_api.model.mapper.BookMapper;
import ab.interview.library_api.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class Book_Test {
    @InjectMocks
    private BookBusiness bookBusiness;

    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void name() {
    }

    @Test
    public void test_registerNewBook_Success() {
        AddBookInDTO addBookInDTO = createAddBookObject();
        Book book = createNewBookObject();
        BookOutDTO bookOutDTO = createNewBookOutDTO();

        when(bookService.addBookValidator(addBookInDTO.getIsbnNumber(), addBookInDTO.getTitle(), addBookInDTO.getAuthor()))
                .thenReturn(0L);

        when(bookMapper.createNewBook(addBookInDTO)).thenReturn(book);
        when(bookService.addBook(book)).thenReturn(book);

        when(bookMapper.convertToDTO(book)).thenReturn(bookOutDTO);

        BookOutDTO result = bookBusiness.addBook(addBookInDTO);
        System.out.println("result===>" + result);

        assertNotNull(result);
    }

    @Test
    public void test_registerNewBook_Failed() {
        AddBookInDTO addBookInDTO = createAddBookObject();

        when(bookService.addBookValidator(addBookInDTO.getIsbnNumber(), addBookInDTO.getTitle(), addBookInDTO.getAuthor()))
                .thenReturn(2L);

        assertThrows(ResponseStatusException.class, () -> {
            bookBusiness.addBook(addBookInDTO);
        });
    }

    @Test
    public void test_getAllBooks_Success() {
        List<Book> mockBookOutput = new ArrayList<>();
        Book book1 = createNewBookObject();
        Book book2 = createNewBookObject();
        mockBookOutput.add(book1);
        mockBookOutput.add(book2);

        List<BookOutDTO> mockBookOutDTOOutput = new ArrayList<>();
        BookOutDTO bookOutDTO1 = createNewBookOutDTO();
        BookOutDTO bookOutDTO2 = createNewBookOutDTO();
        mockBookOutDTOOutput.add(bookOutDTO1);
        mockBookOutDTOOutput.add(bookOutDTO2);

        when(bookService.findAllBooks()).thenReturn(mockBookOutput);
        when(bookMapper.convertToDTOList(mockBookOutput)).thenReturn(mockBookOutDTOOutput);

        List<BookOutDTO> result = bookBusiness.getAllBooks();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void test_getAllBooks_Failed() {
        List<Book> mockBookOutput = new ArrayList<>();
        Book book1 = createNewBookObject();
        Book book2 = createNewBookObject();
        mockBookOutput.add(book1);
        mockBookOutput.add(book2);

        List<BookOutDTO> mockBookOutDTOOutput = new ArrayList<>();

        when(bookService.findAllBooks()).thenReturn(mockBookOutput);
        when(bookMapper.convertToDTOList(mockBookOutput)).thenReturn(mockBookOutDTOOutput);

        List<BookOutDTO> result = bookBusiness.getAllBooks();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private AddBookInDTO createAddBookObject() {
        AddBookInDTO addBookInDTO = new AddBookInDTO();
        addBookInDTO.setIsbnNumber("000");
        addBookInDTO.setBookType(1L);
        addBookInDTO.setAuthor("Test Author");
        addBookInDTO.setTitle("Test Title");

        return addBookInDTO;
    }

    private Book createNewBookObject() {
        Book book = new Book();
        book.setIsbnNumber("000");
        book.setStatus(Book.ACTIVE);
        book.setAuthor("Test Author");
        book.setTitle("Test Title");
        book.setCreatedAt(LocalDate.now());
        book.setUpdatedAt(LocalDate.now());
        return book;
    }

    private BookOutDTO createNewBookOutDTO() {
        BookOutDTO bookOutDTO = new BookOutDTO();
        bookOutDTO.setId(1L);
        bookOutDTO.setAuthor("Test Author");
        bookOutDTO.setTitle("Test Title");
        bookOutDTO.setStatus(Book.ACTIVE);
        bookOutDTO.setIsbnNumber("000");
        bookOutDTO.setCreatedAt(LocalDate.now());
        bookOutDTO.setUpdatedAt(LocalDate.now());

        return bookOutDTO;
    }

}
