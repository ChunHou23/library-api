package ab.interview.library_api.application.borrow;

import ab.interview.library_api.model.Book;
import ab.interview.library_api.model.BorrowRecord;
import ab.interview.library_api.model.Borrower;
import ab.interview.library_api.model.dto.in.BorrowRecordInDTO;
import ab.interview.library_api.model.dto.out.BorrowRecordOutDTO;
import ab.interview.library_api.model.mapper.BookMapper;
import ab.interview.library_api.model.mapper.BorrowRecordMapper;
import ab.interview.library_api.model.mapper.BorrowerMapper;
import ab.interview.library_api.service.BookService;
import ab.interview.library_api.service.BorrowRecordService;
import ab.interview.library_api.service.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Slf4j
@Service
public class BookBorrowBusiness implements BorrowBusiness {
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowerService borrowerService;
    @Autowired
    private BorrowRecordService borrowRecordService;

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BorrowerMapper borrowerMapper;
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Override
    public BorrowRecordOutDTO borrowResource(BorrowRecordInDTO borrowRecordInDTO) {
        try {
            Book book = bookService.findBookById(borrowRecordInDTO.getResourceId());
            if (Objects.isNull(book)) {
                log.warn("[Invalid Book]: Unable to find book id of {}", borrowRecordInDTO.getResourceId());
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Unable to find book");
            }

            Borrower borrower = borrowerService.findBorrowerById(borrowRecordInDTO.getBorrowerId());
            if (Objects.isNull(borrower)) {
                log.warn("[Invalid Borrower]: Unable to find borrower id of {}", borrowRecordInDTO.getBorrowerId());
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Unable to find borrower");
            }

            Boolean isAvailable = borrowRecordService.validateResourceAvailability(book.getId(), BorrowRecord.ACTIVE);
            if (!isAvailable) {
                log.warn("[Invalid Borrow]: Book id of {} is not available", borrowRecordInDTO.getResourceId());
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Unable to borrow book");
            }

            BorrowRecord borrowRecord = borrowRecordService.addNewBorrowRecord(borrowRecordMapper.createNewBorrowRecord(borrowRecordInDTO));

            BorrowRecordOutDTO borrowRecordOutDTO = borrowRecordMapper.convertToDTO(borrowRecord);
            borrowRecordOutDTO.setBook(bookMapper.convertToDTO(book));
            borrowRecordOutDTO.setBorrower(borrowerMapper.convertToDTO(borrower));

            return borrowRecordOutDTO;
        } catch (Exception e) {
            log.warn("borrowBook: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to borrow book");
        }
    }

    @Override
    public BorrowRecordOutDTO returnResource(BorrowRecordInDTO borrowRecordInDTO) {
        if (Objects.isNull(borrowRecordInDTO.getId())) {
            log.warn("returnResource: borrow record id is null");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to return resource");
        }

        BorrowRecord borrowRecord = borrowRecordService.findById(borrowRecordInDTO.getId());
        if (Objects.isNull(borrowRecord)) {
            log.warn("returnResource: borrow record not found");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to return resource");
        }

        if (Objects.equals(borrowRecord.getStatus(), BorrowRecord.INACTIVE)) {
            log.warn("returnResource: resource has been returned previously");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to return resource");
        }

        if (!Objects.equals(borrowRecord.getBorrowerId(), borrowRecordInDTO.getBorrowerId())
        || !Objects.equals(borrowRecord.getResourceId(), borrowRecordInDTO.getResourceId())) {
            log.warn("return Resource: unmatched borrow record | borrowRecord: {} | input: {}", borrowRecord, borrowRecordInDTO);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to match resource/borrower");
        }

        borrowRecordService.returnResource(borrowRecord.getId());

        BorrowRecord updatedBorrowRecord = borrowRecordService.findById(borrowRecordInDTO.getId());

        Book book = bookService.findBookById(updatedBorrowRecord.getResourceId());
        Borrower borrower = borrowerService.findBorrowerById(updatedBorrowRecord.getBorrowerId());

        BorrowRecordOutDTO borrowRecordOutDTO = borrowRecordMapper.convertToDTO(updatedBorrowRecord);
        borrowRecordOutDTO.setBook(bookMapper.convertToDTO(book));
        borrowRecordOutDTO.setBorrower(borrowerMapper.convertToDTO(borrower));

        return borrowRecordOutDTO;
    }
}
