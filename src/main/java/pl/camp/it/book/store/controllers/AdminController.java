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
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class AdminController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBookService bookService;

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
        IBookService.AddBookResult result = this.bookService.addBook(book);
        if(result == IBookService.AddBookResult.PIECES_ADDED) {
            this.sessionObject.setInfo("Zwiększono ilość sztuk !!");
        } else if(result == IBookService.AddBookResult.BOOK_ADDED) {
            this.sessionObject.setInfo("Dodano nową książkę !!");
        }
        return "redirect:/addProduct";
    }

    @RequestMapping(value = "/editBook/{id}", method = RequestMethod.GET)
    public String editBookPage(@PathVariable int id, Model model) {
        Book book = this.bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("user", this.sessionObject.getUser());
        return "editBook";
    }

    @RequestMapping(value = "/editBook/{id}", method = RequestMethod.POST)
    public String editBook(@ModelAttribute Book book, @PathVariable int id) {
        book.setId(id);
        this.bookService.updateBook(book);

        return "redirect:/main";
    }
}
