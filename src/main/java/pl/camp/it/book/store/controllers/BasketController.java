package pl.camp.it.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.book.store.database.IBookRepository;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBasketService;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@Controller
public class BasketController {

    @Autowired
    IBasketService basketService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/addToBasket/{id}", method = RequestMethod.GET)
    public String addBookToBasket(@PathVariable int id) {
        this.basketService.addToBasket(id);
        return "redirect:/main";
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String showBasket(Model model) {
        model.addAttribute("books", this.sessionObject.getBasket());
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("bill", this.basketService.calculateBill());
        return "basket";
    }
}
