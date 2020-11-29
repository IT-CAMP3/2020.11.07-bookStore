package pl.camp.it.book.store.database.impl;

import org.springframework.stereotype.Component;
import pl.camp.it.book.store.database.IBookRepository;
import pl.camp.it.book.store.model.Book;

import java.util.ArrayList;
import java.util.List;

public class ListBookRepositoryImpl implements IBookRepository {

    private final List<Book> books = new ArrayList<>();

    public ListBookRepositoryImpl() {
        /*this.books.add(new Book("Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                10,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));

        this.books.add(new Book("Python. Wprowadzenie. Wydanie V",
                "Mark Lutz",
                15,
                "978-83-283-6150-8",
                109.85,
                Book.Category.OTHER));

        this.books.add(new Book("Uczenie maszynowe z użyciem Scikit-Learn i TensorFlow. Wydanie II",
                "Aurélien Géron",
                5,
                "978-83-283-6002-0",
                83.85,
                Book.Category.OTHER));

        this.books.add(new Book("Java. Podstawy. Wydanie XI",
                "Cay S. Horstmann",
                20,
                "978-83-283-5778-5",
                64.35,
                Book.Category.JAVA));*/
    }

    @Override
    public List<Book> getAllBooks() {
        return this.books;
    }

    @Override
    public List<Book> getBooksByCategory(Book.Category category) {
        //TODO Do zrobienia
        return null;
    }

    @Override
    public Book getBookByISBN(String isbn) {
        for(Book book : this.books) {
            if(book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null;
    }

    @Override
    public void addBook(Book book) {
        this.books.add(book);
    }

    @Override
    public void updateBook(Book book) {
        //TODO do zrobienia
    }
}
