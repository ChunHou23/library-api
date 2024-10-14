package ab.interview.library_api;

import ab.interview.library_api.application.BorrowerBusiness;
import ab.interview.library_api.model.Borrower;
import ab.interview.library_api.model.dto.in.AddBorrowerInDTO;
import ab.interview.library_api.model.dto.out.BorrowerOutDTO;
import ab.interview.library_api.model.mapper.BorrowerMapper;
import ab.interview.library_api.service.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class Borrower_Test {

    @InjectMocks
    private BorrowerBusiness borrowerBusiness;

    @MockBean
    private BorrowerMapper borrowerMapper;

    @MockBean
    private BorrowerService borrowerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_addNewBorrower_Success() {
        AddBorrowerInDTO addBorrowerInDTO = createNewAddBorrowerInDTO();
        Borrower borrower = createNewBorrower();
        BorrowerOutDTO borrowerOutDTO = createNewBorrowerOutDTO();

        when(borrowerMapper.createBorrower(addBorrowerInDTO)).thenReturn(borrower);
        when(borrowerService.addBorrower(borrower)).thenReturn(borrower);
        when(borrowerMapper.convertToDTO(borrower)).thenReturn(borrowerOutDTO);

        BorrowerOutDTO result = borrowerBusiness.addBorrower(addBorrowerInDTO);

        assertNotNull(result);
    }

    @Test
    public void test_searchByName_Success() {
        List<Borrower> borrowerList = new ArrayList<>();
        Borrower borrower1 = createNewBorrower();
        borrowerList.add(borrower1);

        List<BorrowerOutDTO> borrowerOutDTOList = new ArrayList<>();
        borrowerOutDTOList.add(createNewBorrowerOutDTO());

        when(borrowerService.searchByName("Test Name")).thenReturn(borrowerList);
        when(borrowerMapper.convertToDTOList(borrowerList)).thenReturn(borrowerOutDTOList);

        List<BorrowerOutDTO> result = borrowerBusiness.searchByName("Test Name");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void test_searchByName_Empty() {
        List<Borrower> borrowerList = new ArrayList<>();
        Borrower borrower1 = createNewBorrower();
        borrowerList.add(borrower1);

        when(borrowerService.searchByName("Test Name")).thenReturn(new ArrayList<>());
        when(borrowerMapper.convertToDTOList(borrowerList)).thenReturn(new ArrayList<>());

        List<BorrowerOutDTO> result = borrowerBusiness.searchByName("Test Name");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private Borrower createNewBorrower() {
        Borrower borrower = new Borrower();
        borrower.setName("Test Name");
        borrower.setStatus(Borrower.ACTIVE);

        return borrower;
    }

    private BorrowerOutDTO createNewBorrowerOutDTO() {
        BorrowerOutDTO borrowerOutDTO = new BorrowerOutDTO();
        borrowerOutDTO.setName("Test Name");
        borrowerOutDTO.setStatus(Borrower.ACTIVE);

        return borrowerOutDTO;
    }

    private AddBorrowerInDTO createNewAddBorrowerInDTO() {
        AddBorrowerInDTO addBorrowerInDTO = new AddBorrowerInDTO();
        addBorrowerInDTO.setName("Test Name");

        return addBorrowerInDTO;
    }
}
