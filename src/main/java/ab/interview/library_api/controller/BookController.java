package ab.interview.library_api.controller;

import ab.interview.library_api.application.BookBusiness;
import ab.interview.library_api.application.borrow.BorrowBusiness;
import ab.interview.library_api.model.dto.in.BorrowRecordInDTO;
import ab.interview.library_api.model.dto.in.AddBookInDTO;
import ab.interview.library_api.model.dto.out.BookOutDTO;
import ab.interview.library_api.model.dto.out.BorrowRecordOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookBusiness bookBusiness;

    @Autowired
    @Qualifier("bookBorrowBusiness")
    private BorrowBusiness borrowBusiness;

    @PostMapping("/add")
    public ResponseEntity<BookOutDTO> addBook(@RequestBody AddBookInDTO addBookInDTO) {
        BookOutDTO bookOutDTO = bookBusiness.addBook(addBookInDTO);
        return new ResponseEntity<>(bookOutDTO, HttpStatus.OK);
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecordOutDTO> borrowBook(@RequestBody BorrowRecordInDTO borrowRecordInDTO) {
        BorrowRecordOutDTO borrowRecordOutDTO = borrowBusiness.borrowResource(borrowRecordInDTO);
        return new ResponseEntity<>(borrowRecordOutDTO, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowRecordOutDTO> returnBook(@RequestBody BorrowRecordInDTO borrowRecordInDTO) {
        BorrowRecordOutDTO borrowRecordOutDTO = borrowBusiness.returnResource(borrowRecordInDTO);
        return new ResponseEntity<>(borrowRecordOutDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookOutDTO>> getAllBook() {
        List<BookOutDTO> bookOutDTOList = bookBusiness.getAllBooks();
        return new ResponseEntity<>(bookOutDTOList, HttpStatus.OK);
    }
}
