package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.IOrderService;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBookDAO bookDAO;

    @Autowired
    IOrderDAO orderDAO;

    @Override
    public void confirmOrder() {
        //Pobieramy koszyk
        List<Book> orderedBooks = this.sessionObject.getBasket();

        for(Book bookFromBasket : orderedBooks) {
            Book bookFromDB = this.bookDAO.getBookById(bookFromBasket.getId());
            if(bookFromDB.getPieces() < bookFromBasket.getPieces()) {
                return;
            }
        }

        //tworzymy zamowienie
        Order order = new Order();
        //dodajemy usera do zamowienia
        order.setUser(this.sessionObject.getUser());
        //wyliczamy kwote zamowienia
        double bill = 0;
        for(Book book : orderedBooks) {
            bill = bill + book.getPrice() * book.getPieces();
        }
        order.setPrice(bill);
        //ustawiamy status zamowienia
        order.setStatus(Order.Status.ORDERED);
        //tworzymy pozycje zamowienia na podstawie koszyka
        for(Book book : orderedBooks) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setPieces(book.getPieces());
            orderPosition.setOrder(order);
            orderPosition.setBook(book);

            order.getPositions().add(orderPosition);
        }

        this.orderDAO.persistOrder(order);

        for(Book book : orderedBooks) {
            Book bookFromDB = this.bookDAO.getBookById(book.getId());
            bookFromDB.setPieces(bookFromDB.getPieces() - book.getPieces());
            this.bookDAO.updateBook(bookFromDB);
        }

        this.sessionObject.getBasket().clear();
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        return this.orderDAO.getOrdersByUser(this.sessionObject.getUser().getId());
    }
}
