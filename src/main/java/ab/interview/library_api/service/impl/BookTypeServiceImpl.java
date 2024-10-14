package ab.interview.library_api.service.impl;

import ab.interview.library_api.model.BookType;
import ab.interview.library_api.repository.BookTypeRepository;
import ab.interview.library_api.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookTypeServiceImpl implements BookTypeService {
    @Autowired
    private BookTypeRepository bookTypeRepository;

    public BookType findBookById(Long id) {
        return bookTypeRepository.findById(id).orElse(null);
    }
}
