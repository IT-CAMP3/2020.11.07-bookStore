package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.User;

import java.util.List;

public interface IOrderService {
    void confirmOrder();
    List<Order> getOrdersForCurrentUser();
    List<Order> getAllOrders();
    List<Order> getOrdersForUserById(int id);
    void saveOrder(Order order);
    Order getOrderById(int id);
    void deleteOrder(Order order);
    void updateOrder(Order order);
}
