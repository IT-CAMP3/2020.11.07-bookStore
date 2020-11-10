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

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    IBookRepository bookRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model) {
        if(sessionObject.isLogged()) {
            model.addAttribute("books", this.bookRepository.getAllBooks());
            return "main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/java", method = RequestMethod.GET)
    public String java(Model model) {
        model.addAttribute("books", this.bookRepository.getJavaBooks());
        return "main";
    }

    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public String other(Model model) {
        model.addAttribute("books", this.bookRepository.getOtherBooks());
        return "main";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam String filter,
                         Model model) {

        List<Book> filteredBooks = this.bookRepository.getBooksByFilter(filter);
        model.addAttribute("books", filteredBooks);

        return "main";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}
