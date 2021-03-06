package by.tms.bootstore.service;

import by.tms.bootstore.dao.BookDAO;
import by.tms.bootstore.entity.books.Book;
import by.tms.bootstore.entity.books.Genres;
import by.tms.bootstore.entity.books.Review;
import by.tms.bootstore.service.DTO.BookDTO;
import by.tms.bootstore.service.DTO.BookReviewDTO;
import by.tms.bootstore.service.DTO.GenresForBookDTO;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {


    private final BookDAO bookDAO;
    private final ModelMapper modelMapper;


    public BookService(BookDAO bookDAO, ModelMapper modelMapper) {
        this.bookDAO = bookDAO;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void createBook(Book book) {
        KeyHolder keyHolder = bookDAO.saveBook(book);
        long idBook = Objects.requireNonNull(keyHolder.getKey()).longValue();
        book.setId(idBook);
        bookDAO.saveGenres(convertToGenresDTO(book));
        bookDAO.saveReview(book);
        bookDAO.saveBookReviewDB(convertToBookReviewDTO(book));
    }

    private List<GenresForBookDTO> convertToGenresDTO(Book book) {
        List<GenresForBookDTO> genresForBookDTOList = new ArrayList<>();
        for (Genres genres: book.getGenres()
             ) {
            GenresForBookDTO genresForBookDTO = new GenresForBookDTO();
            genresForBookDTO.setIdBook(book.getId());
            genresForBookDTO.setIdGenres(genres.getId());
            genresForBookDTOList.add(genresForBookDTO);
        }
       return genresForBookDTOList;
    }

    private List<BookReviewDTO> convertToBookReviewDTO(Book book) {
        List<BookReviewDTO> bookReviewDTOArrayList = new ArrayList<>();
        for (Review review: book.getReview()
        ) {
           BookReviewDTO bookReviewDTO = new BookReviewDTO();
           bookReviewDTO.setIdBook(book.getId());
           bookReviewDTO.setIdReview(review.getId());
           bookReviewDTOArrayList.add(bookReviewDTO);
        }
        return bookReviewDTOArrayList;
    }

    public Book convertToBook (BookDTO bookDTO, List<String> stringListId){
        Book book = modelMapper.map(bookDTO, Book.class);
        List<Genres> genresList = new ArrayList<>();
        for (String stringId: stringListId) {
            genresList.add(new Genres(Long.parseLong(stringId)));
        }
        book.setGenres(genresList);
        return book;
    }


    public void updateBook(Book book) {
    }

    public Book getBookById(Long id) {
        bookDAO.getBook(id);
        bookDAO.getGenresDTO(id);
        return new Book();
    }

    public List<Book> getAllBooksByName(String name) {
        return new ArrayList<Book>();
    }

    public List<Book> getAllBooksByAuthor(String author) {
        return new ArrayList<Book>();
    }

    public List<Book> getAllBooksByFormat(String format) {
        return new ArrayList<Book>();
    }

    public List<Book> getAllBooksByPublisher(String publisher) {
        return new ArrayList<Book>();
    }

    // для фильтрации в дальнейшем, чтобы выборка была из одного ИЛИ НЕСКОЛЬКИХ жанров
    public List<Book> getAllBooksByGenres(List<Genres> genresList) {
        return new ArrayList<Book>();
    }

    public List<Book> getAllOnlyPositiveReviews() {
        return new ArrayList<Book>();
    }

    public List<Book> getAllOnlyNegativeReviews() {
        return new ArrayList<Book>();
    }

    public List<Book> getAllBookByCostRange(double min, double max) {
        return new ArrayList<Book>();
    }

    public List<Book> getAllInStock() {
        return new ArrayList<Book>();
    }

    //type - возрастание или убывание
    public List<Book> sortByName(String type) {
        return new ArrayList<Book>();
    }

    //type - возрастание или убывание
    public List<Book> sortByAuthor(String type) {
        return new ArrayList<Book>();
    }

    //type - возрастание или убывание
    public List<Book> sortByPublicationDate(String type) {
        return new ArrayList<Book>();
    }

    //type - возрастание или убывание
    public List<Book> sortByCost(String type) {
        return new ArrayList<Book>();
    }

    // сортировка по рекомендую/нерекомендую
    public List<Book> sortByReviews(String typeOfReview) {
        return new ArrayList<Book>();
    }

    public List<Integer> listYear() {
        List<Integer> listYear = new ArrayList<>();
        for (int i = 1960; i < 2021; i++) {
            listYear.add(i);
        }
        return listYear;
    }

    public List<Genres> allGenres(){
        return bookDAO.getAllGenres();
    }

}

