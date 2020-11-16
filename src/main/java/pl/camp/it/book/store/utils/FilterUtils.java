package pl.camp.it.book.store.utils;

import pl.camp.it.book.store.model.Book;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static List<Book> filterBooks(List<Book> books, String filter) {
        if(filter == null) {
            return books;
        }

        List<Book> filteredBooks = new ArrayList<>();

        for(Book book : books) {
            if(book.getTitle().toUpperCase().contains(filter.toUpperCase()) ||
                    book.getAuthor().toUpperCase().contains(filter.toUpperCase())) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }
}
