package pl.camp.it.book.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.camp.it.book.store.database.IBookRepository;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.services.IBookService;
import pl.camp.it.book.store.session.SessionObject;
import pl.camp.it.book.store.tasks.SaveProductToDataBase;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

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
    public String addProduct(@ModelAttribute Book book, @RequestParam MultipartFile obrazek) {
        System.out.println(obrazek);
        try {
            String filePath = "E:\\IT-CAMP3\\plikiZFormularza\\img3333333.png";
            obrazek.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        IBookService.AddBookResult result = this.bookService.addBook(book);
        if(result == IBookService.AddBookResult.PIECES_ADDED) {
            this.sessionObject.setInfo("Zwiększono ilość sztuk !!");
        } else if(result == IBookService.AddBookResult.BOOK_ADDED) {
            this.sessionObject.setInfo("Dodano nową książkę !!");
        }
        //new Thread(new SaveProductToDataBase()).start();
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
