package pl.camp.it.book.store.dao;

import pl.camp.it.book.store.model.Book;

import java.util.List;

public interface IBookDAO {
    Book getBookByISBN(String isbn);
    void updateBook(Book book);
    void persistBook(Book book);
    Book getBookById(int id);
    List<Book> getBooksByCategory(Book.Category category);
    List<Book> getAllBooks();
    void deleteBook(Book book);
}
