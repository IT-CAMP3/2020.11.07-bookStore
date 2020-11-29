package pl.camp.it.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.book.store.database.IBookRepository;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.session.SessionObject;
import pl.camp.it.book.store.utils.FilterUtils;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    IBookRepository bookRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String commonRedirect() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model, @RequestParam(defaultValue = "none") String category) {
        if(sessionObject.isLogged()) {
            switch (category) {
                case "java":
                    model.addAttribute("books",
                            FilterUtils.filterBooks(this.bookRepository.getBooksByCategory(Book.Category.JAVA),
                                    this.sessionObject.getFilter()));
                    break;
                case "other":
                    model.addAttribute("books",
                            FilterUtils.filterBooks(this.bookRepository.getBooksByCategory(Book.Category.OTHER),
                                    this.sessionObject.getFilter()));
                    break;

                    default:
                        model.addAttribute("books",
                                FilterUtils.filterBooks(this.bookRepository.getAllBooks(),
                                        this.sessionObject.getFilter()));
                        break;
            }
            model.addAttribute("user", this.sessionObject.getUser());
            model.addAttribute("filter", this.sessionObject.getFilter());
            return "main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam String filter) {
        if(sessionObject.isLogged()) {
            this.sessionObject.setFilter(filter);
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Model model) {
        if(sessionObject.isLogged()) {
            model.addAttribute("user", this.sessionObject.getUser());
            return "contact";
        } else {
            return "redirect:/login";
        }
    }
}
