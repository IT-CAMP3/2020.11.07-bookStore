package pl.camp.it.book.store.rest.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.rest.api.model.AddOrderRequest;
import pl.camp.it.book.store.rest.api.model.RestOrderPosition;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.services.IOrderPositionService;
import pl.camp.it.book.store.services.IOrderService;

@RestController
@RequestMapping(value = "/api/orderpositions")
public class OrderPositionRestController {

    @Autowired
    IOrderPositionService orderPositionService;

    @Autowired
    IOrderService orderService;

    @Autowired
    IBookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public List<RestOrderPosition> getAllOrderPositions() {
        return convertOrderPositionListToRestOrderPositionList(this.orderPositionService.getAllOrderPositions());
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<RestOrderPosition>> getOrderPositionsForOrderById(@PathVariable("id") int orderId) {
        Order order = this.orderService.getOrderById(orderId);
        if(order == null) {
            return ResponseEntity.notFound().build();
        }

        List<OrderPosition> orderPositions = this.orderPositionService.getOrderPositionsForOrderById(orderId);
        return ResponseEntity.ok(convertOrderPositionListToRestOrderPositionList(orderPositions));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestOrderPosition> getOrderPositionById(@PathVariable int id) {
        OrderPosition orderPosition = this.orderPositionService.getOrderPositionById(id);
        if(orderPosition == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertOrderPositionToRestOrderPosition(orderPosition));
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
    public ResponseEntity<RestOrderPosition> addOrderPositionToOrder(@PathVariable int orderId,
                                                                     @RequestBody AddOrderRequest.AddOrderPositionModel orderPositionModel) {
        Order order = this.orderService.getOrderById(orderId);
        if(order == null) {
            return ResponseEntity.badRequest().build();
        }

        OrderPosition orderPosition = new OrderPosition();
        orderPosition.setPieces(orderPositionModel.getPieces());
        Book book = this.bookService.getBookById(orderPositionModel.getBookId());

        if(book == null) {
            return ResponseEntity.badRequest().build();
        }

        orderPosition.setBook(book);
        orderPosition.setOrder(order);

        order.getPositions().add(orderPosition);

        double bill = 0;
        for(OrderPosition currentOrderPosition : order.getPositions()) {
            bill = bill + currentOrderPosition.getPieces() * currentOrderPosition.getBook().getPrice();
        }
        order.setPrice(bill);

        this.orderService.updateOrder(order);

        return ResponseEntity.ok(convertOrderPositionToRestOrderPosition(orderPosition));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrderPosition(@PathVariable int id) {
        OrderPosition orderPosition = this.orderPositionService.getOrderPositionById(id);
        if(orderPosition == null) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderPosition.getOrder();
        order.getPositions().remove(orderPosition);

        double bill = 0;
        for(OrderPosition currentOrderPosition : order.getPositions()) {
            bill = bill + currentOrderPosition.getPieces() * currentOrderPosition.getBook().getPrice();
        }
        order.setPrice(bill);

        if(order.getPositions().size() > 0) {
            this.orderService.updateOrder(order);
        } else {
            this.orderService.deleteOrder(order);
        }
        this.orderPositionService.removeOrderPosition(orderPosition);
        return ResponseEntity.ok().build();
    }

    private List<RestOrderPosition> convertOrderPositionListToRestOrderPositionList(List<OrderPosition> orderPositions) {
        List<RestOrderPosition> restOrderPositions = new ArrayList<>();

        for(OrderPosition orderPosition : orderPositions) {
            restOrderPositions.add(convertOrderPositionToRestOrderPosition(orderPosition));
        }

        return restOrderPositions;
    }

    private RestOrderPosition convertOrderPositionToRestOrderPosition(OrderPosition orderPosition) {
        RestOrderPosition restOrderPosition = new RestOrderPosition();
        restOrderPosition.setId(orderPosition.getId());
        restOrderPosition.setPieces(orderPosition.getPieces());
        restOrderPosition.setOrder("http://localhost:8080/api/orders/" + orderPosition.getOrder().getId());
        restOrderPosition.setBook("http://localhost:8080/books/" + orderPosition.getBook().getId());

        return restOrderPosition;
    }
}
