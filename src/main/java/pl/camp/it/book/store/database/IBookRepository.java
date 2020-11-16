package pl.camp.it.book.store.database;

import pl.camp.it.book.store.model.Book;

import java.util.List;

public interface IBookRepository {
    List<Book> getAllBooks();
    List<Book> getJavaBooks();
    List<Book> getOtherBooks();
    Book getBookByISBN(String isbn);
    void addBook(Book book);
}
