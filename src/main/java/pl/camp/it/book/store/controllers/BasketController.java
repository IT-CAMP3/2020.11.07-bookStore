package pl.camp.it.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.book.store.database.IBookRepository;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class BasketController {

    @Autowired
    IBookRepository bookRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/addToBasket/{isbn}", method = RequestMethod.GET)
    public String addBookToBasket(@PathVariable String isbn) {

        for(Book book : this.sessionObject.getBasket()) {
            if(book.getIsbn().equals(isbn)) {
                book.setPieces(book.getPieces()+1);
                return "redirect:/main";
            }
        }

        Book book = (Book) this.bookRepository.getBookByISBN(isbn).clone();
        book.setPieces(1);
        this.sessionObject.getBasket().add(book);
        return "redirect:/main";
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String showBasket(Model model) {
        model.addAttribute("books", this.sessionObject.getBasket());
        model.addAttribute("user", this.sessionObject.getUser());
        double bill = 0;
        for(Book book : this.sessionObject.getBasket()) {
            bill = bill + book.getPrice() * book.getPieces();
        }
        model.addAttribute("bill", bill);
        return "basket";
    }
}
