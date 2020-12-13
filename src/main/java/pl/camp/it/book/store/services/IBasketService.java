package pl.camp.it.book.store.services;

public interface IBasketService {
    void addToBasket(int bookId);
    double calculateBill();
    void removeBookFromBasket(int bookId);
}
