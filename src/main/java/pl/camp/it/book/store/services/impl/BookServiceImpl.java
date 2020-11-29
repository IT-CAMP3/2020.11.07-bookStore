package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;
import pl.camp.it.book.store.utils.FilterUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    IBookDAO bookDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public AddBookResult addBook(Book book) {
        Book bookFromDB = this.bookDAO.getBookByISBN(book.getIsbn());
        if(bookFromDB == null) {
            this.bookDAO.persistBook(book);
            return AddBookResult.BOOK_ADDED;
        } else {
            bookFromDB.setPieces(bookFromDB.getPieces() + book.getPieces());
            this.bookDAO.updateBook(bookFromDB);
            return AddBookResult.PIECES_ADDED;
        }
    }

    @Override
    public Book getBookByISBN(String isbn) {
        return this.bookDAO.getBookByISBN(isbn);
    }

    @Override
    public Book getBookById(int id) {
        return this.bookDAO.getBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        this.bookDAO.updateBook(book);
    }

    @Override
    public List<Book> getBooksByCategoryWithFilter(String category) {
        switch (category) {
            case "java":
                return FilterUtils.filterBooks(this.bookDAO.getBooksByCategory(Book.Category.JAVA),
                                this.sessionObject.getFilter());
            case "other":
                return FilterUtils.filterBooks(this.bookDAO.getBooksByCategory(Book.Category.OTHER),
                        this.sessionObject.getFilter());

            default:
                return FilterUtils.filterBooks(this.bookDAO.getAllBooks(),
                        this.sessionObject.getFilter());
        }
    }
}
