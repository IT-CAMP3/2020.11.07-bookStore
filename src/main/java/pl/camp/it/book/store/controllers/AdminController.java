package pl.camp.it.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.book.store.database.IBookRepository;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class AdminController {

    @Autowired
    IBookRepository bookRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProductForm(Model model) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());
        model.addAttribute("book", new Book());
        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Book book) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        Book bookFromDB = this.bookRepository.getBookByISBN(book.getIsbn());

        if(bookFromDB != null) {
            bookFromDB.setPieces(bookFromDB.getPieces() + book.getPieces());
            this.sessionObject.setInfo("Zwiększono ilość sztuk !!");
        } else {
            if(book.getTitle().equals("") ||
                    book.getAuthor().equals("") ||
                    book.getIsbn().equals("") ||
            book.getPrice() == 0.0) {
                this.sessionObject.setInfo("Uzupełnij formularz !!");
            } else {
                this.bookRepository.addBook(book);
                this.sessionObject.setInfo("Dodano nową książkę !!");
            }
        }
        return "redirect:/addProduct";
    }

    @RequestMapping(value = "/editBook/{isbn}", method = RequestMethod.GET)
    public String editBookPage(@PathVariable String isbn, Model model) {
        Book book = this.bookRepository.getBookByISBN(isbn);
        model.addAttribute("book", book);
        model.addAttribute("user", this.sessionObject.getUser());
        return "editBook";
    }

    @RequestMapping(value = "/editBook/{isbn}", method = RequestMethod.POST)
    public String editBook(@ModelAttribute Book book, @PathVariable String isbn) {
        Book bookFromDataBase = this.bookRepository.getBookByISBN(isbn);
        bookFromDataBase.setAuthor(book.getAuthor());
        bookFromDataBase.setPieces(book.getPieces());
        bookFromDataBase.setCategory(book.getCategory());
        bookFromDataBase.setPrice(book.getPrice());

        return "redirect:/main";
    }
}
