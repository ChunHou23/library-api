package ab.interview.library_api.model.mapper;

import ab.interview.library_api.model.Book;
import ab.interview.library_api.model.dto.in.AddBookInDTO;
import ab.interview.library_api.model.dto.out.BookOutDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookMapper {
    public Book createNewBook(AddBookInDTO addBookInDTO) {
        Book book = new Book();
        book.setAuthor(addBookInDTO.getAuthor());
        book.setTitle(addBookInDTO.getTitle());
        book.setStatus(Book.ACTIVE);
        book.setCreatedAt(LocalDate.now());
        book.setUpdatedAt(LocalDate.now());
        book.setIsbnNumber(addBookInDTO.getIsbnNumber());

        log.info("Creating Book: {}", book);
        return book;
    }

    public List<BookOutDTO> convertToDTOList(List<Book> bookList) {
        return bookList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookOutDTO convertToDTO(Book book) {
        BookOutDTO bookOutDTO = new BookOutDTO();
        log.info("convertToDTO | Book : {}", book);
        try {
            BeanUtils.copyProperties(book, bookOutDTO);
        } catch (Exception e) {
            log.warn("unable to convert to DTO", e);
            return null;
        }

        log.info("convertToDTO: {}", bookOutDTO);
        return bookOutDTO;
    }
}
