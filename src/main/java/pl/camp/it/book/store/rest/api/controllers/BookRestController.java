package pl.camp.it.book.store.rest.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.book.store.model.Book;

import java.util.List;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.utils.FilterUtils;

@RestController
@RequestMapping(value = "/books")
public class BookRestController {

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = this.bookService.getBookById(id);
        if(book == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(book);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if(this.bookService.getBookByISBN(book.getIsbn()) != null) {
            return ResponseEntity.status(409).build();
        } else {
            this.bookService.addBook(book);
            return ResponseEntity.ok(book);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        if(this.bookService.getBookById(book.getId()) == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.bookService.updateBook(book);
            return ResponseEntity.ok(book);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBook(@PathVariable int id) {
        Book book = this.bookService.getBookById(id);
        if(book == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.bookService.deleteBook(book);
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<Book> getBooksWithFilter(@RequestParam String pattern) {
        return FilterUtils.filterBooks(this.bookService.getAllBooks(), pattern);
    }
}
