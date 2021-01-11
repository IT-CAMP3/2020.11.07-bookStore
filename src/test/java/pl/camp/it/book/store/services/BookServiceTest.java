package pl.camp.it.book.store.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.camp.it.book.store.configuration.TestConfiguration;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.dao.IUserDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfiguration.class})
public class BookServiceTest {

    @Autowired
    IBookService bookService;

    @MockBean
    IBookDAO bookDAO;

    @MockBean
    IUserDAO userDAO;

    @MockBean
    IOrderDAO orderDAO;

    @Resource
    SessionObject sessionObject;

    @Test
    public void addNewBookTest() {
        Book book = new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                5,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA);
        Mockito.when(this.bookDAO.getBookByISBN(Mockito.any())).thenReturn(null);


        IBookService.AddBookResult result = this.bookService.addBook(book);

        Mockito.verify(this.bookDAO, Mockito.times(1)).persistBook(book);
        Assert.assertEquals(IBookService.AddBookResult.BOOK_ADDED, result);
    }

    @Test
    public void addExistingBookTest() {
        Book book = new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                5,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA);
        Mockito.when(this.bookDAO.getBookByISBN(Mockito.any())).thenReturn(new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                5,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));

        IBookService.AddBookResult result = this.bookService.addBook(book);

        Mockito.verify(this.bookDAO, Mockito.times(1)).updateBook(Mockito.any());
        Assert.assertEquals(IBookService.AddBookResult.PIECES_ADDED, result);
    }

    @Test
    public void getJavaBooksWithFilter() {
        String category = "java";

        this.bookService.getBooksByCategoryWithFilter(category);

        Mockito.verify(this.bookDAO, Mockito.times(1)).getBooksByCategory(Book.Category.JAVA);
    }

    @Test
    public void getOtherBooksWithFilter() {
        String category = "other";

        this.bookService.getBooksByCategoryWithFilter(category);

        Mockito.verify(this.bookDAO, Mockito.times(1)).getBooksByCategory(Book.Category.OTHER);
    }

    @Test
    public void getBooksWithoutCategory() {
        String category = "asdfgsdfgh";

        this.bookService.getBooksByCategoryWithFilter(category);

        Mockito.verify(this.bookDAO, Mockito.times(1)).getAllBooks();
    }

    @Test
    public void getBooksWithNullCategory() {
        String category = null;

        this.bookService.getBooksByCategoryWithFilter(category);

        Mockito.verify(this.bookDAO, Mockito.times(1)).getAllBooks();
    }
}
