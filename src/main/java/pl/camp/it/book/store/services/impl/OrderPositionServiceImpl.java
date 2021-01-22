package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.dao.IOrderPositionDAO;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.services.IOrderPositionService;

import java.util.List;

@Service
public class OrderPositionServiceImpl implements IOrderPositionService {

    @Autowired
    IOrderPositionDAO orderPositionDAO;

    @Override
    public List<OrderPosition> getAllOrderPositions() {
        return this.orderPositionDAO.getAllOrderPositions();
    }

    @Override
    public List<OrderPosition> getOrderPositionsForOrderById(int orderId) {
        return this.orderPositionDAO.getOrderPositionsForOrderById(orderId);
    }

    @Override
    public OrderPosition getOrderPositionById(int id) {
        return this.orderPositionDAO.getOrderPositionById(id);
    }

    @Override
    public void removeOrderPosition(OrderPosition orderPosition) {
        this.orderPositionDAO.removeOrderPosition(orderPosition);
    }
}
