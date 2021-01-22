package pl.camp.it.book.store.rest.api.controllers;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.Order;
import pl.camp.it.book.store.model.OrderPosition;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.rest.api.model.AddOrderRequest;
import pl.camp.it.book.store.rest.api.model.RestOrder;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.services.IOrderService;
import pl.camp.it.book.store.services.IUserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderRestController {

    @Autowired
    IOrderService orderService;

    @Autowired
    IUserService userService;

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<RestOrder> getAllOrders() {
        return convertDdModelListToRestModelList(this.orderService.getAllOrders());
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<RestOrder>> getOrdersForUser(@PathVariable int id) {
        if(this.userService.getUserById(id) == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(convertDdModelListToRestModelList(this.orderService.getOrdersForUserById(id)));
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<RestOrder> addOrder(@RequestBody AddOrderRequest orderRequest) {
        User user = userService.getUserById(orderRequest.getUserId());
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }
        Order order = new Order();
        order.setStatus(Order.Status.ORDERED);
        order.setPrice(orderRequest.getPrice());
        order.setUser(user);

        for(AddOrderRequest.AddOrderPositionModel addOrderPositionModel : orderRequest.getPositions()) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setOrder(order);
            orderPosition.setPieces(addOrderPositionModel.getPieces());
            Book book = this.bookService.getBookById(addOrderPositionModel.getBookId());
            if(book == null) {
                return ResponseEntity.badRequest().build();
            }
            orderPosition.setBook(book);
            order.getPositions().add(orderPosition);
        }

        this.orderService.saveOrder(order);

        return ResponseEntity.ok(convertDbModelToRestModel(order));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOrder(@PathVariable int id) {
        Order order = this.orderService.getOrderById(id);
        if(order == null) {
            return ResponseEntity.notFound().build();
        }
        this.orderService.deleteOrder(order);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestOrder> getOrderById(@PathVariable int id) {
        Order order = this.orderService.getOrderById(id);
        if(order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertDbModelToRestModel(order));
    }

    private List<RestOrder> convertDdModelListToRestModelList(List<Order> orders) {
        List<RestOrder> restOrders = new ArrayList<>();

        for (Order order : orders) {
            restOrders.add(convertDbModelToRestModel(order));
        }

        return restOrders;
    }

    private RestOrder convertDbModelToRestModel(Order order) {
        RestOrder restOrder = new RestOrder();
        restOrder.setId(order.getId());
        restOrder.setPrice(order.getPrice());
        restOrder.setStatus(order.getStatus());
        restOrder.setUser("http://localhost:8080/users/" + order.getUser().getId());

        for (OrderPosition orderPosition : order.getPositions()) {
            restOrder.getPositions().add("http://localhost:8080/api/orderpositions/" + orderPosition.getId());
        }

        return restOrder;
    }
}
