package pl.camp.it.book.store.dao;

import pl.camp.it.book.store.model.OrderPosition;
import java.util.List;

public interface IOrderPositionDAO {
    List<OrderPosition> getAllOrderPositions();
    List<OrderPosition> getOrderPositionsForOrderById(int orderId);
    OrderPosition getOrderPositionById(int id);
    void removeOrderPosition(OrderPosition orderPosition);
}
