package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.Book;

import java.util.List;

public interface IBookService {
    AddBookResult addBook(Book book);
    Book getBookByISBN(String isbn);
    Book getBookById(int id);
    void updateBook(Book book);
    List<Book> getBooksByCategoryWithFilter(String category);
    List<Book> getAllBooks();
    void deleteBook(Book book);

    enum AddBookResult {
        PIECES_ADDED,
        BOOK_ADDED
    }
}
