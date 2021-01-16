package pl.camp.it.book.store.rest.api.model;

import pl.camp.it.book.store.model.Order;

import java.util.ArrayList;
import java.util.List;

public class RestOrder {
    private int id;
    private String user;
    private List<String> positions = new ArrayList<>();
    private double price;
    private pl.camp.it.book.store.model.Order.Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order.Status getStatus() {
        return status;
    }

    public void setStatus(Order.Status status) {
        this.status = status;
    }
}
