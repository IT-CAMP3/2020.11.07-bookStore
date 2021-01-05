package pl.camp.it.book.store.utils;

import org.junit.Assert;
import org.junit.Test;
import pl.camp.it.book.store.model.Book;

import java.util.ArrayList;
import java.util.List;

public class FilterUtilsTest {

    @Test
    public void filterBooksWithNullPattern() {
        String pattern = null;
        List<Book> books = generateTestBooksList();

        List<Book> result = FilterUtils.filterBooks(books, pattern);

        Assert.assertSame(books, result);
    }

    @Test
    public void filterBooksByTitle() {
        String pattern = "Python";
        List<Book> books = generateTestBooksList();
        List<Book> expectedResult = generateResultBooksList();

        List<Book> result = FilterUtils.filterBooks(books, pattern);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void filterBookByAuthor() {
        String pattern = "Lutz";
        List<Book> books = generateTestBooksList();
        List<Book> expectedResult = generateResultBooksList();

        List<Book> result = FilterUtils.filterBooks(books, pattern);

        Assert.assertEquals(expectedResult, result);
    }

    private List<Book> generateTestBooksList() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1,
                "Czysty kod. Podręcznik dobrego programisty",
                "Robert C. Martin",
                10,
                "978-83-283-0234-1",
                44.85,
                Book.Category.JAVA));
        books.add(new Book(2,
                "Python. Wprowadzenie. Wydanie V",
                "Mark Lutz",
                15,
                "978-83-283-6150-8",
                109.85,
                Book.Category.OTHER));
        books.add(new Book(3,
                "Uczenie maszynowe z użyciem Scikit-Learn i TensorFlow. Wydanie II",
                "Aurélien Géron",
                5,
                "978-83-283-6002-0",
                83.85,
                Book.Category.OTHER));
        books.add(new Book(4,
                "Java. Podstawy. Wydanie XI",
                "Cay S. Horstmann",
                20,
                "978-83-283-5778-5",
                64.35,
                Book.Category.JAVA));
        return books;
    }

    private List<Book> generateResultBooksList() {
        List<Book> expectedResult = new ArrayList<>();
        expectedResult.add(new Book(2,
                "Python. Wprowadzenie. Wydanie V",
                "Mark Lutz",
                15,
                "978-83-283-6150-8",
                109.85,
                Book.Category.OTHER));
        return expectedResult;
    }
}
