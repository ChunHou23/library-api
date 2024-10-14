package ab.interview.library_api.service.impl;

import ab.interview.library_api.model.Borrower;
import ab.interview.library_api.repository.BorrowerRepository;
import ab.interview.library_api.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    public Borrower addBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Borrower findBorrowerById(Long id) {
        return borrowerRepository.findById(id).orElse(null);
    }

    public List<Borrower> searchByName(String name) {
        return borrowerRepository.searchByName(name);
    }
}
