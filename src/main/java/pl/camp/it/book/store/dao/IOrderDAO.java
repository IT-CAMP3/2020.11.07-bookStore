package pl.camp.it.book.store.dao;

import pl.camp.it.book.store.model.Order;
import java.util.List;

public interface IOrderDAO {
    void persistOrder(Order order);
    List<Order> getOrdersByUser(int userId);
    List<Order> getAllOrders();
}
