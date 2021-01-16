package pl.camp.it.book.store.rest.api.model;

import java.util.ArrayList;
import java.util.List;

public class AddOrderRequest {
    private int userId;
    private List<AddOrderPositionModel> positions = new ArrayList<>();
    private double price;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<AddOrderPositionModel> getPositions() {
        return positions;
    }

    public void setPositions(List<AddOrderPositionModel> positions) {
        this.positions = positions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static class AddOrderPositionModel {
        private int pieces;
        private int bookId;

        public int getPieces() {
            return pieces;
        }

        public void setPieces(int pieces) {
            this.pieces = pieces;
        }

        public int getBookId() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }
    }
}
