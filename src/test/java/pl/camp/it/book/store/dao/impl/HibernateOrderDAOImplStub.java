package pl.camp.it.book.store.dao.impl;

import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.model.Order;

import java.util.List;

public class HibernateOrderDAOImplStub implements IOrderDAO {
    @Override
    public void persistOrder(Order order) {

    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        return null;
    }
}