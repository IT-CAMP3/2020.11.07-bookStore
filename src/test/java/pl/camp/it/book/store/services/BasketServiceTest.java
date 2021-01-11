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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfiguration.class})
public class BasketServiceTest {

    @MockBean
    IBookDAO bookDAO;

    @MockBean
    IUserDAO userDAO;

    @MockBean
    IOrderDAO orderDAO;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBasketService basketService;

    @Test
    public void addExistingBookToBasketTest() {
        this.sessionObject.setBasket(generateFakeBasket());
        int bookIdToAdd = 1;
        int expectedAddedBookPieces = 2;
        int expectedOtherBookPieces = 1;
        int expectedBasketSize = 4;

        this.basketService.addToBasket(bookIdToAdd);

        for(Book book : this.sessionObject.getBasket()) {
            if(book.getId() == bookIdToAdd) {
                Assert.assertEquals(expectedAddedBookPieces, book.getPieces());
            } else {
                Assert.assertEquals(expectedOtherBookPieces, book.getPieces());
            }
        }
        Assert.assertEquals(expectedBasketSize, this.sessionObject.getBasket().size());
    }

    @Test
    public void addNewBookToBasketTest() {
        this.sessionObject.setBasket(generateFakeBasket());
        int bookIdToAdd = 5;
        int expectedBooksPieces = 1;
        int expectedBasketSize = 5;
        Mockito.when(this.bookDAO.getBookById(5)).thenReturn(new Book(5,
                "Adobe Illustrator PL. Oficjalny podręcznik. Edycja 2020",
                "Brian Wood",
                15,
                "978-83-283-7062-3",
                99.00,
                Book.Category.OTHER));

        this.basketService.addToBasket(bookIdToAdd);

        for(Book book : this.sessionObject.getBasket()) {
            Assert.assertEquals(expectedBooksPieces, book.getPieces());
        }
        Assert.assertEquals(expectedBasketSize, this.sessionObject.getBasket().size());
    }

    @Test
    public void calculateBillTest() {
        this.sessionObject.setBasket(generateFakeBasket());
        double expectedBill = 302.9;

        double resultBill = this.basketService.calculateBill();

        Assert.assertEquals(expectedBill, resultBill, 0.001);
    }

    @Test
    public void removeExistingBookFromBasketTest() {
        this.sessionObject.setBasket(generateFakeBasket());
        int bookIdToRemove = 2;
        int expectedBasketSize = 3;

        this.basketService.removeBookFromBasket(bookIdToRemove);

        Assert.assertEquals(expectedBasketSize, this.sessionObject.getBasket().size());
        for(Book book : sessionObject.getBasket()) {
            if(book.getId() == bookIdToRemove) {
                Assert.fail();
            }
        }
    }

    @Test
    public void removeNotExistingBookFromBasketTest() {
        this.sessionObject.setBasket(generateFakeBasket());
        int bookIdToRemove = 15;
        int expectedBasketSize = 4;

        this.basketService.removeBookFromBasket(bookIdToRemove);

        Assert.assertEquals(expectedBasketSize, this.sessionObject.getBasket().size());
    }

    private List<Book> generateFakeBasket() {
        List<Book> basket = new ArrayList<>();
        basket.add(new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                1,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));

        basket.add(new Book(2,
                "Python. Wprowadzenie. Wydanie V",
                "Mark Lutz",
                1,
                "978-83-283-6150-8",
                109.85,
                Book.Category.OTHER));

        basket.add(new Book(3,
                "Uczenie maszynowe z użyciem Scikit-Learn i TensorFlow. Wydanie II",
                "Aurélien Géron",
                1,
                "978-83-283-6002-0",
                83.85,
                Book.Category.OTHER));

        basket.add(new Book(4,
                "Java. Podstawy. Wydanie XI",
                "Cay S. Horstmann",
                1,
                "978-83-283-5778-5",
                64.35,
                Book.Category.JAVA));

        return basket;
    }
}
