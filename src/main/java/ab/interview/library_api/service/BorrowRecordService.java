package ab.interview.library_api.service;

import ab.interview.library_api.model.BorrowRecord;

public interface BorrowRecordService {
    BorrowRecord addNewBorrowRecord(BorrowRecord borrowRecord);
    BorrowRecord findById(Long id);
    void returnResource(Long id);
    Boolean validateResourceAvailability(Long resourceId, Integer status);
}
