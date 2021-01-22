package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.OrderPosition;
import java.util.List;

public interface IOrderPositionService {
    List<OrderPosition> getAllOrderPositions();
    List<OrderPosition> getOrderPositionsForOrderById(int orderId);
    OrderPosition getOrderPositionById(int id);
    void removeOrderPosition(OrderPosition orderPosition);
}
