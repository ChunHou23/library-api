package ab.interview.library_api;

import ab.interview.library_api.application.borrow.BookBorrowBusiness;
import ab.interview.library_api.model.Book;
import ab.interview.library_api.model.BorrowRecord;
import ab.interview.library_api.model.Borrower;
import ab.interview.library_api.model.dto.in.BorrowRecordInDTO;
import ab.interview.library_api.model.dto.out.BookOutDTO;
import ab.interview.library_api.model.dto.out.BorrowRecordOutDTO;
import ab.interview.library_api.model.dto.out.BorrowerOutDTO;
import ab.interview.library_api.model.mapper.BookMapper;
import ab.interview.library_api.model.mapper.BorrowRecordMapper;
import ab.interview.library_api.model.mapper.BorrowerMapper;
import ab.interview.library_api.service.BookService;
import ab.interview.library_api.service.BorrowRecordService;
import ab.interview.library_api.service.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowStrategy_Test {
    @InjectMocks
    BookBorrowBusiness bookBorrowBusiness;

    @Mock
    BookService bookService;
    @Mock
    BorrowerService borrowerService;
    @Mock
    BorrowRecordService borrowRecordService;

    @Mock
    BookMapper bookMapper;
    @Mock
    BorrowerMapper borrowerMapper;
    @Mock
    BorrowRecordMapper borrowRecordMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_borrowResource_Success() {
        BorrowRecordInDTO borrowRecordInDTO = createInObject();
        BorrowRecord borrowRecord = createNewBorrowRecord();


        when(bookService.findBookById(borrowRecordInDTO.getResourceId())).thenReturn(createActiveBookObject());
        when(borrowerService.findBorrowerById(borrowRecordInDTO.getBorrowerId())).thenReturn(createActiveBorrowerObject());
        when(borrowRecordService.validateResourceAvailability(borrowRecordInDTO.getResourceId(), BorrowRecord.ACTIVE)).thenReturn(true);

        when(borrowRecordMapper.createNewBorrowRecord(borrowRecordInDTO)).thenReturn(borrowRecord);
        when(borrowRecordService.addNewBorrowRecord(borrowRecord)).thenReturn(borrowRecord);

        when(borrowRecordMapper.convertToDTO(borrowRecord)).thenReturn(createOutObject());

        BorrowRecordOutDTO result = bookBorrowBusiness.borrowResource(borrowRecordInDTO);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    public void test_borrowResource_Failed_InvalidBook() {
        BorrowRecordInDTO borrowRecordInDTO = createInObject();
        when(bookService.findBookById(borrowRecordInDTO.getResourceId())).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            bookBorrowBusiness.borrowResource(borrowRecordInDTO);
        });
    }

    @Test
    public void test_borrowResource_Failed_InvalidBorrower() {
        BorrowRecordInDTO borrowRecordInDTO = createInObject();
        when(bookService.findBookById(borrowRecordInDTO.getResourceId())).thenReturn(new Book());
        when(borrowerService.findBorrowerById(1L)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            bookBorrowBusiness.borrowResource(borrowRecordInDTO);
        });
    }

    @Test
    public void test_borrowResource_Failed_UnavailableBook() {
        BorrowRecordInDTO borrowRecordInDTO = createInObject();

        when(bookService.findBookById(borrowRecordInDTO.getResourceId())).thenReturn(new Book());
        when(borrowerService.findBorrowerById(1L)).thenReturn(new Borrower());
        when(borrowRecordService.validateResourceAvailability(10L, BorrowRecord.ACTIVE)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            bookBorrowBusiness.borrowResource(borrowRecordInDTO);
        });
    }

    @Test
    public void test_returnResource_Success() {
        BorrowRecordInDTO borrowRecordInDTO = createInObject();
        BorrowRecord mockBorrowRecord = createNewBorrowRecord();

        BorrowRecordOutDTO borrowRecordOutDTO = createOutObject();
        borrowRecordOutDTO.setStatus(BorrowRecord.INACTIVE);

        when(borrowRecordService.findById(borrowRecordInDTO.getId())).thenReturn(mockBorrowRecord);
        when(borrowRecordMapper.convertToDTO(mockBorrowRecord)).thenReturn(borrowRecordOutDTO);

        when(bookService.findBookById(borrowRecordInDTO.getResourceId())).thenReturn(new Book());
        when(borrowerService.findBorrowerById(borrowRecordInDTO.getBorrowerId())).thenReturn(new Borrower());
        when(bookMapper.convertToDTO(any(Book.class))).thenReturn(new BookOutDTO());
        when(borrowerMapper.convertToDTO(any(Borrower.class))).thenReturn(new BorrowerOutDTO());

        BorrowRecordOutDTO result = bookBorrowBusiness.returnResource(borrowRecordInDTO);

        assertNotNull(result);
        assertEquals(BorrowRecord.INACTIVE, result.getStatus());
    }

    @Test
    public void test_returnResource_Failed_InvalidBorrowRecord_1() {
        BorrowRecordInDTO borrowRecordInDTO = new BorrowRecordInDTO();

        assertThrows(ResponseStatusException.class, ()-> {
            bookBorrowBusiness.returnResource(borrowRecordInDTO);
        });
    }

    @Test
    public void test_returnResource_Failed_InvalidBorrowRecord_2() {
        BorrowRecordInDTO borrowRecordInDTO = createInObject();

        when(borrowRecordService.findById(borrowRecordInDTO.getId())).thenReturn(null);
        assertThrows(ResponseStatusException.class, ()-> {
            bookBorrowBusiness.returnResource(borrowRecordInDTO);
        });
    }

    private BorrowRecord createNewBorrowRecord() {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(10L);
        borrowRecord.setResourceId(10L);
        borrowRecord.setBorrowerId(1L);
        borrowRecord.setStatus(BorrowRecord.ACTIVE);

        return borrowRecord;
    }


    private Book createActiveBookObject() {
        Book book = new Book();
        book.setId(10L);
        book.setStatus(Book.ACTIVE);

        return book;
    }

    private Borrower createActiveBorrowerObject() {
        Borrower borrower = new Borrower();
        borrower.setStatus(Borrower.ACTIVE);

        return borrower;
    }

    private BorrowRecordInDTO createInObject() {
        BorrowRecordInDTO borrowRecordInDTO = new BorrowRecordInDTO();
        borrowRecordInDTO.setId(10L);
        borrowRecordInDTO.setBorrowerId(1L);
        borrowRecordInDTO.setResourceId(10L);

        return borrowRecordInDTO;
    }

    private BorrowRecordOutDTO createOutObject() {
        BorrowRecordOutDTO borrowRecordOutDTO = new BorrowRecordOutDTO();
        borrowRecordOutDTO.setStatus(BorrowRecord.ACTIVE);

        return borrowRecordOutDTO;
    }
}
