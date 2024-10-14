package ab.interview.library_api.service;

import ab.interview.library_api.model.BookType;

public interface BookTypeService {
    BookType findBookById(Long id);
}
