package ab.interview.library_api.application.borrow;

import ab.interview.library_api.model.dto.in.BorrowRecordInDTO;
import ab.interview.library_api.model.dto.out.BorrowRecordOutDTO;

public interface BorrowBusiness {
    BorrowRecordOutDTO borrowResource(BorrowRecordInDTO borrowRecordInDTO);
    BorrowRecordOutDTO returnResource(BorrowRecordInDTO borrowRecordInDTO);
}
