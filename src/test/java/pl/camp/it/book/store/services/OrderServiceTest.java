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
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.session.SessionObject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfiguration.class})
public class OrderServiceTest {

    @Autowired
    IOrderService orderService;

    @MockBean
    IUserDAO userDAO;

    @MockBean
    IBookDAO bookDAO;

    @MockBean
    IOrderDAO orderDAO;

    @Resource
    SessionObject sessionObject;

    @Test
    public void confirmBasketWithIncorrectBookPieces() {
        this.sessionObject.setBasket(generateFakeBasket());
        Mockito.when(this.bookDAO.getBookById(1)).thenReturn(new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                5,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));

        orderService.confirmOrder();

        Mockito.verify(this.orderDAO, Mockito.never()).persistOrder(Mockito.any());
    }

    @Test
    public void confirmCorrectBasket() {
        this.sessionObject.setBasket(generateFakeBasket());
        Mockito.when(this.bookDAO.getBookById(1)).thenReturn(new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                20,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));
        Mockito.when(this.bookDAO.getBookById(2)).thenReturn(new Book(2,
                "Python. Wprowadzenie. Wydanie V",
                "Mark Lutz",
                20,
                "978-83-283-6150-8",
                109.85,
                Book.Category.OTHER));
        Mockito.when(this.bookDAO.getBookById(3)).thenReturn(new Book(3,
                "Uczenie maszynowe z użyciem Scikit-Learn i TensorFlow. Wydanie II",
                "Aurélien Géron",
                5,
                "978-83-283-6002-0",
                83.85,
                Book.Category.OTHER));
        Mockito.when(this.bookDAO.getBookById(4)).thenReturn(new Book(4,
                "Java. Podstawy. Wydanie XI",
                "Cay S. Horstmann",
                50,
                "978-83-283-5778-5",
                64.35,
                Book.Category.JAVA));
        int expectedBasketSize = 0;
        int expectedPersisOrderCalls = 1;
        int expectedUpdateBookCalls = 4;

        this.sessionObject.setUser(generateFakeUser());

        this.orderService.confirmOrder();

        Mockito.verify(this.orderDAO, Mockito.times(expectedPersisOrderCalls)).persistOrder(Mockito.any());
        Mockito.verify(this.bookDAO, Mockito.times(expectedUpdateBookCalls)).updateBook(Mockito.any());
        Assert.assertEquals(expectedBasketSize, this.sessionObject.getBasket().size());
        Mockito.verify(this.bookDAO, Mockito.times(2)).getBookById(1);
        Mockito.verify(this.bookDAO, Mockito.times(2)).getBookById(2);
        Mockito.verify(this.bookDAO, Mockito.times(2)).getBookById(3);
        Mockito.verify(this.bookDAO, Mockito.times(2)).getBookById(4);
    }

    @Test
    public void getOrdersForCurrentUserTest() {
        User user = generateFakeUser();
        this.sessionObject.setUser(user);

        this.orderService.getOrdersForCurrentUser();

        Mockito.verify(this.orderDAO, Mockito.times(1)).getOrdersByUser(1);
    }

    private List<Book> generateFakeBasket() {
        List<Book> basket = new ArrayList<>();
        basket.add(new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                10,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));

        basket.add(new Book(2,
                "Python. Wprowadzenie. Wydanie V",
                "Mark Lutz",
                15,
                "978-83-283-6150-8",
                109.85,
                Book.Category.OTHER));

        basket.add(new Book(3,
                "Uczenie maszynowe z użyciem Scikit-Learn i TensorFlow. Wydanie II",
                "Aurélien Géron",
                5,
                "978-83-283-6002-0",
                83.85,
                Book.Category.OTHER));

        basket.add(new Book(4,
                "Java. Podstawy. Wydanie XI",
                "Cay S. Horstmann",
                20,
                "978-83-283-5778-5",
                64.35,
                Book.Category.JAVA));

        return basket;
    }

    private User generateFakeUser() {
        User user = new User();
        user.setId(1);
        user.setLogin("admin");
        user.setName("Admin");
        user.setPass("admin");
        user.setRole(User.Role.ADMIN);
        user.setSurname("Admin");

        return user;
    }
}
