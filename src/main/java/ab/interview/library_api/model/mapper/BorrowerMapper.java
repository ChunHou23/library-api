package ab.interview.library_api.model.mapper;

import ab.interview.library_api.model.Borrower;
import ab.interview.library_api.model.dto.in.AddBorrowerInDTO;
import ab.interview.library_api.model.dto.out.BorrowerOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BorrowerMapper {

    public Borrower createBorrower(AddBorrowerInDTO addBorrowerInDTO) {
        Borrower borrower = new Borrower();

        borrower.setName(addBorrowerInDTO.getName());
        borrower.setEmail(addBorrowerInDTO.getEmail());
        borrower.setStatus(Borrower.ACTIVE);
        borrower.setCreatedAt(LocalDate.now());
        borrower.setUpdatedAt(LocalDate.now());

        log.info("Created Borrower: {}", borrower);
        return borrower;
    }

    public List<BorrowerOutDTO> convertToDTOList(List<Borrower> borrowerList) {
        return borrowerList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BorrowerOutDTO convertToDTO(Borrower borrower) {
        BorrowerOutDTO borrowerOutDTO = new BorrowerOutDTO();

        try {
            BeanUtils.copyProperties(borrower, borrowerOutDTO);
        } catch (Exception e) {
            log.warn("unable to convert to DTO", e);
            return null;
        }

        log.info("convertToDTO: {}", borrowerOutDTO);
        return borrowerOutDTO;
    }
}
