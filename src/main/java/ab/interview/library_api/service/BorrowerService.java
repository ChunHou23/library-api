package ab.interview.library_api.service;

import ab.interview.library_api.model.Borrower;

import java.util.List;

public interface BorrowerService {
    Borrower addBorrower(Borrower borrower);
    Borrower findBorrowerById(Long id);
    List<Borrower> searchByName(String name);
}
