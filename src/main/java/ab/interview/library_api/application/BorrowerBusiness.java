package ab.interview.library_api.application;

import ab.interview.library_api.model.Borrower;
import ab.interview.library_api.model.dto.in.AddBorrowerInDTO;
import ab.interview.library_api.model.dto.out.BorrowerOutDTO;
import ab.interview.library_api.model.mapper.BorrowerMapper;
import ab.interview.library_api.service.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BorrowerBusiness {
    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private BorrowerMapper borrowerMapper;

    public BorrowerOutDTO addBorrower(AddBorrowerInDTO addBorrowerInDTO) {
        try {
            Borrower createdBorrower = borrowerService.addBorrower(borrowerMapper.createBorrower(addBorrowerInDTO));
            return borrowerMapper.convertToDTO(createdBorrower);
        } catch (Exception e) {
            log.warn("addBorrower", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register borrower");
        }
    }

    public List<BorrowerOutDTO> searchByName(String name) {
        try {
            log.info("Searching name: {}", name);
            List<Borrower> borrowerList = borrowerService.searchByName(name);
            if (CollectionUtils.isEmpty(borrowerList)) {
                log.info("searchByName: empty list with name: {}", name);
                return new ArrayList<>();
            }

            return borrowerMapper.convertToDTOList(borrowerList);
        } catch (Exception e) {
            log.warn("searchByName", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to search borrower");
        }
    }
}
