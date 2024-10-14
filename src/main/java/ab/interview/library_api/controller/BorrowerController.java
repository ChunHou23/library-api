package ab.interview.library_api.controller;

import ab.interview.library_api.application.BorrowerBusiness;
import ab.interview.library_api.model.dto.in.AddBorrowerInDTO;
import ab.interview.library_api.model.dto.out.BorrowerOutDTO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(value = "borrower api")
@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    @Autowired
    private BorrowerBusiness borrowerBusiness;

    @PostMapping("/add")
//    @ApiOperation(value = "Register new borrower")
    public ResponseEntity<BorrowerOutDTO> addBorrower(@RequestBody AddBorrowerInDTO addBorrowerInDTO) {
        BorrowerOutDTO borrowerOutDTO = borrowerBusiness.addBorrower(addBorrowerInDTO);
        return new ResponseEntity<>(borrowerOutDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
//    @ApiOperation(value = "Search Borrower By Name")
    public ResponseEntity<List<BorrowerOutDTO>> searchByName(@RequestParam String name) {
        List<BorrowerOutDTO> borrowerOutDTOList = borrowerBusiness.searchByName(name);
        return new ResponseEntity<>(borrowerOutDTOList, HttpStatus.OK);
    }
}
