package pl.camp.it.book.store.dao;

import pl.camp.it.book.store.model.User;
import java.util.List;

public interface IUserDAO {
    User getUserByLogin(String login);
    void updateUser(User user);
    void persistUser(User user);
    List<User> getAllUsers();
}
