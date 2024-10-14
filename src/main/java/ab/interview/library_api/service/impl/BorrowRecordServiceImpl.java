package ab.interview.library_api.service.impl;

import ab.interview.library_api.model.BorrowRecord;
import ab.interview.library_api.repository.BorrowRecordRepository;
import ab.interview.library_api.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Override
    public BorrowRecord addNewBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    @Override
    public BorrowRecord findById(Long id) {
        return borrowRecordRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void returnResource(Long id) {
        borrowRecordRepository.updateRecordToReturn(id, BorrowRecord.INACTIVE, LocalDate.now());
    }

    @Override
    public Boolean validateResourceAvailability(Long resourceId, Integer status) {
        return borrowRecordRepository.validateResourceAvailability(resourceId, status);
    }
}
