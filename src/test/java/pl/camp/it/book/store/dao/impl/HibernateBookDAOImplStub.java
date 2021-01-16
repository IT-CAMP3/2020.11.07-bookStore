package pl.camp.it.book.store.dao.impl;

import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.model.Book;

import java.util.List;

public class HibernateBookDAOImplStub implements IBookDAO {
    @Override
    public Book getBookByISBN(String isbn) {
        return null;
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void persistBook(Book book) {

    }

    @Override
    public Book getBookById(int id) {
        return null;
    }

    @Override
    public List<Book> getBooksByCategory(Book.Category category) {
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public void deleteBook(Book book) {

    }
}
