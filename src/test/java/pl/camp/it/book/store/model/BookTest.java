package pl.camp.it.book.store.model;

import org.junit.Assert;
import org.junit.Test;

public class BookTest {

    @Test
    public void cloneBookTest() {
        Book book = new Book(4,
                "Java. Podstawy. Wydanie XI",
                "Cay S. Horstmann",
                20,
                "978-83-283-5778-5",
                64.35,
                Book.Category.JAVA);

        Book clonedBook = (Book) book.clone();

        /*Assert.assertEquals(book.getId(), clonedBook.getId());
        Assert.assertEquals(book.getTitle(), clonedBook.getTitle());
        Assert.assertEquals(book.getAuthor(), clonedBook.getAuthor());
        Assert.assertEquals(book.getPieces(), clonedBook.getPieces());
        Assert.assertEquals(book.getIsbn(), clonedBook.getIsbn());
        Assert.assertEquals(book.getPrice(), clonedBook.getPrice(), 0.0);
        Assert.assertEquals(book.getCategory(), clonedBook.getCategory());*/

        Assert.assertEquals(book, clonedBook);
        Assert.assertNotSame(book, clonedBook);
    }
}
