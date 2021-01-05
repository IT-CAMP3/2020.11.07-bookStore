package pl.camp.it.book.store.model;

import javax.persistence.*;

@Entity(name = "tbook")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private int pieces;
    private String isbn;
    private double price;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Book(int id, String title, String author, int pieces, String isbn, double price, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pieces = pieces;
        this.isbn = isbn;
        this.price = price;
        this.category = category;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum Category {
        JAVA,
        OTHER
    }

    @Override
    public Object clone() {
        return new Book(this.id, this.title, this.author, this.pieces, this.isbn, this.price, this.category);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Book{")
                .append("id=")
                .append(id)
                .append(", title='")
                .append(title)
                .append(", author='")
                .append(author)
                .append(", pieces=")
                .append(pieces)
                .append(", isbn='")
                .append(isbn)
                .append(", price=")
                .append(price)
                .append(", category=")
                .append(category)
                .append('}').toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Book) {
            Book book = (Book) obj;
            return this.id == book.getId() &&
            this.title.equals(book.getTitle()) &&
            this.author.equals(book.getAuthor()) &&
            this.pieces == book.getPieces() &&
            this.isbn.equals(book.isbn) &&
            this.price == book.getPrice() &&
            this.category == book.getCategory();
        }

        return false;
    }
}
