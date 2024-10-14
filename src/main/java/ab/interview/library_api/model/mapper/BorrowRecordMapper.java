package ab.interview.library_api.model.mapper;

import ab.interview.library_api.model.BorrowRecord;
import ab.interview.library_api.model.dto.in.BorrowRecordInDTO;
import ab.interview.library_api.model.dto.out.BorrowRecordOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BorrowRecordMapper {

    public BorrowRecord createNewBorrowRecord(BorrowRecordInDTO borrowRecordInDTO) {
        BorrowRecord borrowRecord = new BorrowRecord();

        borrowRecord.setBorrowerId(borrowRecordInDTO.getBorrowerId());
        borrowRecord.setResourceId(borrowRecordInDTO.getResourceId());
        borrowRecord.setCreatedAt(LocalDate.now());
        borrowRecord.setUpdatedAt(LocalDate.now());
        borrowRecord.setStatus(BorrowRecord.ACTIVE);

        return borrowRecord;
    }

    public List<BorrowRecordOutDTO> convertToDTOList(List<BorrowRecord> borrowRecordList) {
        return borrowRecordList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BorrowRecordOutDTO convertToDTO(BorrowRecord borrowRecord) {
        BorrowRecordOutDTO borrowRecordOutDTO = new BorrowRecordOutDTO();

        try {
            BeanUtils.copyProperties(borrowRecord, borrowRecordOutDTO);
        } catch (Exception e) {
            log.warn("unable to convert to DTO", e);
            return null;
        }

        log.info("convertToDTO: {}", borrowRecordOutDTO);
        return borrowRecordOutDTO;
    }
}
