package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBasketService;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@Service
public class BasketServiceImpl implements IBasketService {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBookDAO bookDAO;

    @Override
    public void addToBasket(int bookId) {
        for(Book book : this.sessionObject.getBasket()) {
            if(book.getId() == bookId) {
                book.setPieces(book.getPieces()+1);
                return;
            }
        }

        Book book = this.bookDAO.getBookById(bookId);
        book.setPieces(1);
        this.sessionObject.getBasket().add(book);
    }

    @Override
    public double calculateBill() {
        double bill = 0;
        for(Book book : this.sessionObject.getBasket()) {
            bill = bill + book.getPrice() * book.getPieces();
        }
        return bill;
    }
}
