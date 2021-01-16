package pl.camp.it.book.store.rest.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.services.IUserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById() {
        return new User();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public User addUser(User user) {
        return new User();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public User updateUser(User user) {
        return new User();
    }
}
